package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllRoomsResponse(
    val rooms: List<Room>
) {
    data class Room(
        val id: String,
        @SerializedName("other_user_name") val otherUserName: String,
        @SerializedName("last_message") val lastMessage: String,
        @SerializedName("last_sent_user_id") val lastSentUserId: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String
    )
}
