package com.unitip.mobile.features.chat.presentation.states

import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.shared.domain.models.Session

data class ConversationState(
    val session: Session? = null,
    val isTyping: Boolean = false,
    val isOtherUserTyping: Boolean = false,
    val sendingMessageUUIDs: Set<String> = emptySet(),
    val failedMessageUUIDs: Set<String> = emptySet(),
    val messages: List<Message> = emptyList(),

    val detail: Detail = Detail.Initial,
    val realtimeDetail: RealtimeDetail = RealtimeDetail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }

    sealed interface RealtimeDetail {
        data object Initial : RealtimeDetail
        data object NewMessage : RealtimeDetail
    }
}
