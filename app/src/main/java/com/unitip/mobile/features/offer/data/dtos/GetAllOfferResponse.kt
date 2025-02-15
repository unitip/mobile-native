package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName
import com.unitip.mobile.features.offer.commons.PageInfo

data class GetAllOfferResponse(
    @SerializedName("offers") val offers: List<AllOffer>,
    @SerializedName("page_info") val pageInfo: PageInfo
) {
    data class AllOffer(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("type") val type: String,
        @SerializedName("destination_area") val destinationArea: String,
        @SerializedName("pickup_area") val pickupArea: String,
        @SerializedName("available_until") val availableUntil: String,
        @SerializedName("price") val price: Double,
        @SerializedName("offer_status") val offerStatus: String,
        @SerializedName("freelancer") val freelancer: Freelancer,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String,
        @SerializedName("max_participants") val maxParticipants: Int
    )

    data class Freelancer(
        @SerializedName("id") val id: String = "",
        @SerializedName("name") val name: String = ""
    )
}
//    data class PageInfo(
//        val count: Int,
//        val page: Int,
//        @SerializedName("total_pages") val totalPages: Int,
//        val type: OfferType
//    ) {
//        enum class OfferType(val value: String) {
//            ALL("all"),
//            SINGLE("single"),
//            MULTI("multi");
//
//            companion object {
//                fun fromValue(value: String): OfferType {
//                    return entries.find { it.value == value } ?: ALL
//                }
//            }
//        }
//    }

