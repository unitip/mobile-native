package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CheckRoomResponse(
    @SerializedName("room_id") val roomId: String
)