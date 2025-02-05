package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class GetOfferResponse(
    @SerializedName("offer") val offer: ApiOffer
) {
    data class ApiOffer(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("type") val type: String,
        @SerializedName("available_until") val availableUntil: String,
        @SerializedName("price") val price: Number,
        @SerializedName("destination_area") val destinationArea: String,
        @SerializedName("pickup_area") val pickupArea: String,
        @SerializedName("offer_status") val offerStatus: String,
        @SerializedName("freelancer") val freelancer: ApiFreelancer,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        @SerializedName("applicants_count") val applicantsCount: Int,
        @SerializedName("has_applied") val hasApplied: Boolean,
        @SerializedName("max_participants") val maxParticipants: Int,
        @SerializedName("applicants") val applicants: List<ApiApplicant>,
    )

    data class ApiFreelancer(
        @SerializedName("name") val name: String
    )

    data class ApiApplicant(
        val id: String,
        @SerializedName("customer_id") val customerId: String,
        @SerializedName("customer_name") val customerName: String,
        @SerializedName("pickup_location") val pickupLocation: String,
        @SerializedName("destination_location") val destinationLocation: String,
        @SerializedName("note") val note: String,
        @SerializedName("applicant_status") val applicantStatus: String,
        @SerializedName("final_price") val finalPrice: Int
    )
}