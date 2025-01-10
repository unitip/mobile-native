package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetJobResponse(
    val id: String,
    val title: String,
    val destination: String,
    val note: String,
    val type: String,
    @SerializedName("pickup_location") val pickupLocation: String,
    val applicants: List<Applicant>
) {
    data class Applicant(
        val id: String,
        val name: String,
        val price: Int
    )
}
