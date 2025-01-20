package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class UpdateReadCheckpointResponse(
    val id: String,
    @SerializedName("room_id") val roomId: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("last_read_message_id") val lastReadMessageId: String
)
