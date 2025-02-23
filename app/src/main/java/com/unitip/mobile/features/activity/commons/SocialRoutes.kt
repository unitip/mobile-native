package com.unitip.mobile.features.activity.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class SocialRoutes {
    @Serializable
    data object Create
}