package com.unitip.mobile.features.home.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllDriverOrdersResponse(
    val orders: List<Order>
) {
    data class Order(
        val id: String,
        val title: String,
        val note: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String
    )
}
