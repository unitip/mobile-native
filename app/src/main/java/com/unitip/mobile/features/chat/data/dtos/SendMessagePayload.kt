package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class SendMessagePayload(
    @SerializedName("to_user_id") val toUserId: String,
    val message: String
)
