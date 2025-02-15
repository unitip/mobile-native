package com.unitip.mobile.shared.data.dto

data class BadRequestError(
    val errors: List<Item>,
) {
    data class Item(
        val path: String,
        val message: String,
    )
}