package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllMessagesResponse(
    @SerializedName("other_user") val otherUser: OtherUser,
    val messages: List<Message>
) {
    data class OtherUser(
        val id: String,
        @SerializedName("last_read_message_id") val lastReadMessageId: String
    )

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
