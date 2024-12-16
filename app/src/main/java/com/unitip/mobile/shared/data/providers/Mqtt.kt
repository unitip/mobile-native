package com.unitip.mobile.shared.data.providers

import android.app.Application
import com.unitip.mobile.core.config.MqttConfig
import org.eclipse.paho.android.service.MqttAndroidClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mqtt @Inject constructor(
    private val application: Application,
) {
    fun instance(): MqttAndroidClient = MqttAndroidClient(
        application, MqttConfig.MQTT_SERVER_URI,
        MqttConfig.MQTT_CLIENT_ID
    )
}