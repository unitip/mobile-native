package com.unitip.mobile.features.chat.domain.models

data class UpdateReadCheckpointResult(
    val id: String = "",
    val roomId: String = "",
    val userId: String = "",
    val lastReadMessageId: String = ""
)
