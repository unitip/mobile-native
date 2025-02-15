package com.unitip.mobile.features.home.domain.models

data class OrderDashboard(
    val id: String,
    val title: String,
    val note: String,
    val type: String, // "job" atau "offer"
    val status: String? = null
)
