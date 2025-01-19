package com.unitip.mobile.features.chat.domain.models

data class Room(
    val id: String = "",
    val lastMessage: String = "",
    val lastSentUserId: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val otherUser: OtherUser = OtherUser()
) {
    data class OtherUser(
        val id: String = "",
        val name: String = ""
    )
}
