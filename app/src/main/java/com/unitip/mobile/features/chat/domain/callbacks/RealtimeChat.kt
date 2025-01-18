package com.unitip.mobile.features.chat.domain.callbacks

import com.unitip.mobile.features.chat.domain.models.Message

object RealtimeChat {
    interface MessageListener {
        fun onMessageReceived(message: Message)
    }

    interface TypingStatusListener {
        fun onTypingStatusReceived(isTyping: Boolean)
    }
}