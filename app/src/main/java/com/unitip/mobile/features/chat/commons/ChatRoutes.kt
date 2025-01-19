package com.unitip.mobile.features.chat.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class ChatRoutes {
    @Serializable
    data class Conversation(
        val roomId: String,
        val otherUserId: String,
        val otherUserName: String
    )
}