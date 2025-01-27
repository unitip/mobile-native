package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllJobsResponse(
    val jobs: List<Job>
) {
    data class Job(
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
            val name: String
        )
    }
}





