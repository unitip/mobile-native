package com.unitip.mobile.shared.data.dto

import com.google.gson.annotations.SerializedName

data class GetAllPricesResponse(
    val categories: List<Category>
) {
    data class Category(
        val title: String,
        val prices: List<Price>
    ) {
        data class Price(
            val title: String,
            @SerializedName("min_price") val minPrice: Int,
            @SerializedName("max_price") val maxPrice: Int
        )
    }
}
