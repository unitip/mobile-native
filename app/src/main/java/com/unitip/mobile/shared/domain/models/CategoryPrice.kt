package com.unitip.mobile.shared.domain.models

data class CategoryPrice(
    val title: String,
    val prices: List<Price>
) {
    data class Price(
        val title: String,
        val minPrice: Int,
        val maxPrice: Int
    )
}
