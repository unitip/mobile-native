package com.unitip.mobile.features.chat.domain.models

data class Room(
    val id: String = "",
    val fromUserId: String = "",
    val fromUserName: String = "",
    val message: String = "",
    val lastSentUserId: String = ""
)
