package com.unitip.mobile.features.chat.core

import kotlinx.serialization.Serializable

@Serializable
sealed class ChatRoutes {
    @Serializable
    object Conversation
}