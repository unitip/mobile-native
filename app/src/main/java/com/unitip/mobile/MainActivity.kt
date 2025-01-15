package com.unitip.mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unitip.mobile.shared.commons.Application
import com.unitip.mobile.shared.commons.extensions.isDriver
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.data.providers.MqttProvider
import dagger.hilt.android.AndroidEntryPoint
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var mqttProvider: MqttProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /**
         * publish online status jika user sudah login dan memiliki role
         * sebagai driver
         */
        val session = sessionManager.read()
        val isAuthenticated = session != null

        if (isAuthenticated && session.isDriver()) {
            /**
             * perlu update sistem autentikasi supaya bisa mendapatkan
             * user id, kemudian disimpan ke dalam local storage
             */
            val driverId = "id10" // dummy
            val onlineStatusTopic = "com.unitip/${BuildConfig.MQTT_SECRET}/driver/$driverId/status"

            val options = MqttConnectOptions().apply {
                isAutomaticReconnect = true
                setWill(onlineStatusTopic, "offline".toByteArray(), 2, true)
            }
            val client = mqttProvider.client
            client.connect(
                options = options,
                userContext = null,
                callback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d(TAG, "onSuccess: connected")
                        client.publish(onlineStatusTopic, "online".toByteArray(), 2, true)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        exception?.printStackTrace()
                        Log.d(TAG, "onFailure: ${exception?.message ?: "unknown"}")
                    }
                })
        }

        // memulai aplikasi compose
        setContent {
            Application(isAuthenticated = isAuthenticated)
        }
    }
}
