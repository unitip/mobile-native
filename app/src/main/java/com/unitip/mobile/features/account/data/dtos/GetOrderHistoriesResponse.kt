package com.unitip.mobile.features.account.data.dtos

import com.google.gson.annotations.SerializedName

data class GetOrderHistoriesResponse(
    val orders: List<Order>
) {
    data class Order(
        val id: String,
        val title: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        val customer: Customer
    ) {
        data class Customer(
            val name: String
        )
    }
}
