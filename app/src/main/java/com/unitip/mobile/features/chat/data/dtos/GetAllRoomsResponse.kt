package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class GetAllRoomsResponse(
    val rooms: List<Room>
) {
    data class Room(
        val id: String,
        @SerializedName("from_user_id") val fromUserId: String,
        @SerializedName("from_user_name") val fromUserName: String,
        @SerializedName("to_user_id") val toUserId: String,
        @SerializedName("last_message") val lastMessage: String,
        @SerializedName("last_sent_user_id") val lastSentUserId: String,
        @SerializedName("updated_at") val updatedAt: String
    )
}
