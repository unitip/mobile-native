package com.unitip.mobile.features.offer.data.dtos


data class CreateOfferResponse(
    val message: String,
    val data: Data,
){
    data class Data(
        val success: Boolean,
        val id: String
    )
}

