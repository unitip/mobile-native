package com.unitip.mobile.features.social.data.dtos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SocialResponse(
    val activities: List<Activity>
)

@Serializable
data class Activity(
    @SerializedName("censored_name")
    val censoredName: String,
    @SerializedName("activity_type")
    val activityType: String,
    @SerializedName("time_ago")
    val timeAgo: String
)

