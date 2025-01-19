package com.unitip.mobile.shared.commons.configs

import com.unitip.mobile.BuildConfig

object MqttTopics {
    private const val PREFIX = "com.unitip/${BuildConfig.MQTT_SECRET}"

    object Dashboard {
        fun publishDriverOnlineStatus(driverUserId: String): String =
            "$PREFIX/dashboard/driver/$driverUserId/status"

        fun subscribeDriverOnlineStatus(): String =
            "$PREFIX/dashboard/driver/+/status"
    }

    object Chats {
        fun publishSubscribeMessage(roomId: String): String =
            "$PREFIX/chats/message/$roomId"

        fun publishTypingStatus(currentUserId: String): String =
            "$PREFIX/chats/typing/$currentUserId"

        fun subscribeTypingStatus(otherUserId: String): String =
            "$PREFIX/chats/typing/$otherUserId"
    }
}