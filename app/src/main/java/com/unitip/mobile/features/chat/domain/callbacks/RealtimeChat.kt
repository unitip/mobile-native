package com.unitip.mobile.features.chat.domain.callbacks

import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.domain.models.ReadCheckpoint

object RealtimeChat {
    interface MessageListener {
        fun onMessageReceived(message: Message)
    }

    interface TypingStatusListener {
        fun onTypingStatusReceived(isTyping: Boolean)
    }

    interface ReadCheckpointListener {
        fun onReadCheckpointReceived(readCheckpoint: ReadCheckpoint)
    }
}