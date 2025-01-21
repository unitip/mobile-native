package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class CreateSingleJobPayload(
    val title: String,
    val destination: String,
    val note: String,
    val type: String,
    @SerializedName("pickup_location") val pickupLocation: String,
)
