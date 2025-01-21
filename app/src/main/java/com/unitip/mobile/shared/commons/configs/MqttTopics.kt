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
        // room
        fun publishRoom(otherUserId: String): String =
            "$PREFIX/chats/room/$otherUserId"

        fun subscribeRooms(currentUserId: String): String =
            "$PREFIX/chats/room/$currentUserId"

        // message
        fun publishSubscribeMessage(roomId: String): String =
            "$PREFIX/chats/message/$roomId"

        // typing status
        fun publishTypingStatus(currentUserId: String): String =
            "$PREFIX/chats/typing/$currentUserId"

        fun subscribeTypingStatus(otherUserId: String): String =
            "$PREFIX/chats/typing/$otherUserId"

        // read checkpoint
        fun publishSubscribeReadCheckpoint(roomId: String): String =
            "$PREFIX/chats/read-checkpoint/$roomId"
    }
}