package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class SendMessagePayload(
    val id: String,
    val message: String,
    @SerializedName("other_id") val otherId: String,
    @SerializedName("other_unread_message_count") val otherUnreadMessageCount: Int
)
