package com.unitip.mobile.features.account.domain.models

data class Order(
    val id: String,
    val title: String,
    val createdAt: String,
    val updatedAt: String,
    val otherUser: OtherUser
) {
    data class OtherUser(
        val name: String
    )
}