package com.unitip.mobile.features.chat.domain.models

data class ReadCheckpoint(
    val userId: String = "",
    val lastReadMessageId: String = ""
)
