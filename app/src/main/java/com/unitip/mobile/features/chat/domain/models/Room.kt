package com.unitip.mobile.features.chat.domain.models

data class Room(
    val id: String = "",
    val otherUserName: String = "",
    val lastMessage: String = "",
    val lastSentUserId: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)
