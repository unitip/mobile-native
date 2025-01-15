package com.unitip.mobile.features.home.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.unitip.mobile.BuildConfig
import com.unitip.mobile.features.home.presentation.states.DashboardState
import com.unitip.mobile.shared.data.providers.MqttProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    mqttProvider: MqttProvider
) : ViewModel() {
    companion object {
        private const val TAG = "DashboardViewModel"
    }

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState get() = _uiState.asStateFlow()

    private val client: MqttAndroidClient = mqttProvider.client

    init {
        subscribeToOnlineDriverCount()
    }

    private fun subscribeToOnlineDriverCount() {
        client.setCallback(object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) = Unit
            override fun deliveryComplete(token: IMqttDeliveryToken?) = Unit
            override fun messageArrived(topic: String?, message: MqttMessage?) = Unit
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                /**
                 * ketika client berhasil terhubung dengan broker, maka subscribe ke topic
                 * yang berisi informasi status online driver
                 */
                client.subscribe(
                    topicFilter = "com.unitip/${BuildConfig.MQTT_SECRET}/driver/+/status",
                    qos = 2,
                    messageListener = { topic, message ->
                        val payload = message.toString()
                        if (payload.isNotEmpty()) {
                            Regex("""driver/([^/]+)/""").find(topic).let { result ->
                                if (result != null) {
                                    val driverId = result.groupValues[1]
                                    Log.d(TAG, "driverId: $driverId, message: $message")

                                    _uiState.update {
                                        it.copy(
                                            onlineDriverIds = when (payload) {
                                                "online" -> it.onlineDriverIds + driverId
                                                else -> it.onlineDriverIds - driverId
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        })
    }
}