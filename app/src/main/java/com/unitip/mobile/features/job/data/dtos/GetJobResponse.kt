package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetJobResponse(
    val id: String,
    val title: String,
    @SerializedName("destination_location") val destinationLocation: String,
    @SerializedName("destination_latitude") val destinationLatitude: Double?,
    @SerializedName("destination_longitude") val destinationLongitude: Double?,
    val note: String,
    val service: String,
    @SerializedName("pickup_location") val pickupLocation: String,
    @SerializedName("pickup_latitude") val pickupLatitude: Double?,
    @SerializedName("pickup_longitude") val pickupLongitude: Double?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val customer: Customer
) {
    data class Customer(
        val id: String,
        val name: String
    )
}
