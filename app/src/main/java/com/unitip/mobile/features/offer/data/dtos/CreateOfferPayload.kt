package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class CreateOfferPayload(
    val title: String,
    val description: String,
    val price: Number,
    val type: String,
    @SerializedName("pickup_area") val pickupArea: String,
    @SerializedName("delivery_area") val deliveryArea: String,
    @SerializedName("available_until") val availableUntil: String,
)
