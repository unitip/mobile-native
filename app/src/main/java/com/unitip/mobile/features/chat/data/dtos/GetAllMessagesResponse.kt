package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllMessagesResponse(
    val messages: List<Message>
) {
    data class Message(
        val id: String,
        @SerializedName("from_user_id") val fromUserId: String,
        @SerializedName("to_user_id") val toUserId: String,
        val message: String,
        @SerializedName("is_deleted") val isDeleted: Boolean,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String
    )
}
