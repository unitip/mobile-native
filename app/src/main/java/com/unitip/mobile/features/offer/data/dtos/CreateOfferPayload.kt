package com.unitip.mobile.features.offer.data.dtos

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class CreateOfferPayload(
    val title: String,
    val description: String,
    val type: String,
    @SerializedName("available_until") val avalaibleUntil: String,
    val price: Number,
    val location: String,
    @SerializedName("delivery_area") val deliveryArea: String
)
