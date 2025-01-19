package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllMessagesResponse(
    val messages: List<Message>
) {
    data class Message(
        val id: String,
        val message: String,
        @SerializedName("is_deleted") val isDeleted: Boolean,
        @SerializedName("room_id") val roomId: String,
        @SerializedName("user_id") val userId: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String
    )
}
