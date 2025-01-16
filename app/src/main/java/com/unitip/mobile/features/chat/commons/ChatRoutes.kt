package com.unitip.mobile.features.chat.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class ChatRoutes {
    @Serializable
    object Conversation
}