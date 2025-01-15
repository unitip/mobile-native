package com.unitip.mobile.shared.data.providers

import android.app.Application
import com.unitip.mobile.shared.commons.configs.MqttConfig
import info.mqtt.android.service.MqttAndroidClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttProvider @Inject constructor(
    application: Application,
) {
    val client: MqttAndroidClient = MqttAndroidClient(
        application, MqttConfig.MQTT_SERVER_URI,
        MqttConfig.MQTT_CLIENT_ID
    )
}