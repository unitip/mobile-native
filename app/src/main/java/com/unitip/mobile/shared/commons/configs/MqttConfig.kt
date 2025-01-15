package com.unitip.mobile.shared.commons.configs

class MqttConfig {
    companion object {
        const val MQTT_SERVER_URI = "tcp://broker.emqx.io:1883"
        val MQTT_CLIENT_ID = "com.unitip.mobile-" + System.currentTimeMillis()
    }
}