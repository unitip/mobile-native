package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName
import com.unitip.mobile.features.offer.commons.PageInfo

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
        @SerializedName("max_participants") val maxParticipants: Int
    )

    data class ApiFreelancer(
        @SerializedName("name") val name: String
    )
}