package com.unitip.mobile.shared.commons.configs

import com.unitip.mobile.BuildConfig

object MqttTopics {
    private const val PREFIX = "com.unitip/${BuildConfig.MQTT_SECRET}"

    object Chats {
        fun publishMessage(currentUserId: String, otherUserId: String): String =
            "$PREFIX/chats/message/$otherUserId/$currentUserId"

        fun subscribeMessage(currentUserId: String, otherUserId: String): String =
            "$PREFIX/chats/message/$currentUserId/$otherUserId"

        fun publishTypingStatus(currentUserId: String): String =
            "$PREFIX/chats/typing/$currentUserId"

        fun subscribeTypingStatus(otherUserId: String): String =
            "$PREFIX/chats/typing/$otherUserId"
    }
}