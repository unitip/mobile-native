package com.unitip.mobile.features.job.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllJobApplicationsResponse(
    val applications: List<Application>
) {
    data class Application(
        val id: String,
        val price: Int,
        @SerializedName("bid_note") val bidNote: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        val driver: Driver
    ) {
        data class Driver(
            val name: String
        )
    }
}
