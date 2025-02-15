package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class ApplyJobPayload(
    val price: Int,
    @SerializedName("bid_note") val bidNote: String
)
