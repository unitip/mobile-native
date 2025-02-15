package com.unitip.mobile.features.chat.domain.models

data class Message(
    val id: String = "",
    val message: String = "",
    val isDeleted: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = "",
    val roomId: String = "",
    val userId: String = ""
)
