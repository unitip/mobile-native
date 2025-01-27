package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetJobResponse(
    val id: String,
    val title: String,
    val destination: String,
    val note: String,
    val service: String,
    @SerializedName("pickup_location") val pickupLocation: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val customer: Customer
) {
    data class Customer(
        val id: String,
        val name: String
    )
}
