package com.unitip.mobile.features.offer.domain.models

data class Applicant(
    val id: String,
    val customerId: String,
    val customerName: String,
    val pickupLocation: String,
    val destinationLocation: String,
    val note: String,
    val status: String,
    val finalPrice: Int
)