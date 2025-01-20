package com.unitip.mobile.features.chat.domain.models

data class GetAllMessagesResult(
    val otherUser: OtherUser = OtherUser(),
    val messages: List<Message> = emptyList()
)