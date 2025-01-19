package com.unitip.mobile.features.chat.presentation.states

import com.unitip.mobile.features.chat.domain.models.Room
import com.unitip.mobile.shared.domain.models.Session

data class ChatsState(
    val session: Session? = null,
    val rooms: List<Room> = emptyList(),
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}
