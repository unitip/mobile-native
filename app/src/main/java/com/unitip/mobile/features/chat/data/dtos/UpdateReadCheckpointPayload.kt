package com.unitip.mobile.features.chat.data.dtos

import com.google.gson.annotations.SerializedName

data class UpdateReadCheckpointPayload(
    @SerializedName("last_read_message_id") val lastReadMessageId: String
)