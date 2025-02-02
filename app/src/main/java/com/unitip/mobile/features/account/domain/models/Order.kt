package com.unitip.mobile.features.account.domain.models

data class Order(
    val id: String,
    val title: String,
    val createdAt: String,
    val updatedAt: String,
    val customer: Customer
) {
    data class Customer(
        val name: String
    )
}
