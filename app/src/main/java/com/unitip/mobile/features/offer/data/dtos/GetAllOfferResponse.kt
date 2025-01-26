package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllOfferResponse(
    val offers: List<Offer>,
    @SerializedName("page_info") val pageInfo: PageInfo
) {
    data class Offer(
        val id: String,
        val title: String,
        val description: String,

        val type: String,
        @SerializedName("pickup_area") val pickupArea: String,
        @SerializedName("delivery_area") val deliveryArea: String,
        @SerializedName("available_until") val availableUntil: String,
        val price: Number,
        val freelancer: Freelencer,
        @SerializedName("offer_status") val offerStatus: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
    ) {
        data class Freelencer(
            val name: String
        )
    }

    data class PageInfo(
        val count: Int,
        val page: Int,
        @SerializedName("total_pages") val totalPages: Int,
        val type: OfferType
    ) {
        enum class OfferType(val value: String) {
            ALL("all"),
            SINGLE("single"),
            MULTI("multi");

            companion object {
                fun fromValue(value: String): OfferType {
                    return entries.find { it.value == value } ?: ALL
                }
            }
        }
    }
}
