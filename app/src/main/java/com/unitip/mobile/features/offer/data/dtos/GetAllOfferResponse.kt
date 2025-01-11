package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllOfferResponse(
    val offers: List<Offer>
){
    data class Offer(
        val id:  String,
        val title: String,
        val description: String,
        val type: String,
        @SerializedName("available_until") val availableUntil: String,
        val price: Number,
        val location: String,
        @SerializedName("delivery_area") val deliveryArea: String,
        val freelancer: Freelencer
    ){
        data class Freelencer(
            val name: String
        )
    }
}
