package com.unitip.mobile.features.chat.presentation.states

import com.unitip.mobile.features.chat.domain.models.Room

data class ChatsState(
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
