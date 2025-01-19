package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
    val id: String,
    val message: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
