package com.unitip.mobile.shared.data.providers

import android.app.Application
import com.unitip.mobile.BuildConfig
import info.mqtt.android.service.MqttAndroidClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttProvider @Inject constructor(
    application: Application,
) {
    val client: MqttAndroidClient = MqttAndroidClient(
        context = application,
        serverURI = BuildConfig.MQTT_SERVER_URI,
        clientId = "com.unitip.mobile-" + System.currentTimeMillis()
    )
}