package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class CreateMultiJobPayload(
    val title: String,
    val note: String,
    val service: String,
    @SerializedName("pickup_location") val pickupLocation: String,
)