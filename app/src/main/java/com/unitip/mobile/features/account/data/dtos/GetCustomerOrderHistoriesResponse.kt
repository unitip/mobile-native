package com.unitip.mobile.features.account.data.dtos

import com.google.gson.annotations.SerializedName

data class GetCustomerOrderHistoriesResponse(
    val orders: List<Order>
) {
    data class Order(
        val id: String,
        val title: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        val driver: Driver
    ) {
        data class Driver(
            val name: String
        )
    }
}
