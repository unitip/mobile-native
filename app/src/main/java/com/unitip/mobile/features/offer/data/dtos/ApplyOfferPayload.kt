package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class ApplyOfferPayload(
    @SerializedName("note") val note: String,
    @SerializedName("final_price") val finalPrice: Int,
    @SerializedName("destination_location") val destinationLocation: String,
    @SerializedName("pickup_location") val pickupLocation: String,
    @SerializedName("pickup_latitude") val pickupLatitude: Double,
    @SerializedName("pickup_longitude") val pickupLongitude: Double,
    @SerializedName("destination_latitude") val destinationLatitude: Double,
    @SerializedName("destination_longitude") val destinationLongitude: Double
)