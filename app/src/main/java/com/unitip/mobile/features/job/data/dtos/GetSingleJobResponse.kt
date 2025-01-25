package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetSingleJobResponse(
    val id: String,
    val title: String,
    val destination: String,
    val note: String,
    val service: String,
    @SerializedName("pickup_location") val pickupLocation: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("customer_id") val customerId: String,
    val applications: List<Application>
) {
    data class Application(
        val id: String,
        @SerializedName("freelancer_name") val freelancerName: String,
        val price: Int
    )
}
