package com.unitip.mobile.features.home.data.dtos

data class GetAllCustomerOrdersResponse(
    val orders: List<Order>
) {
    data class Order(
        val id: String,
        val title: String,
        val note: String
    )
}
