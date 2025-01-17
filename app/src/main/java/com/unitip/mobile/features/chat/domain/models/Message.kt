package com.unitip.mobile.features.chat.domain.models

data class Message(
    val id: String = "",
    val fromUserId: String = "",
    val toUserId: String = "",
    val message: String = "",
    val isDeleted: Boolean = false
)
