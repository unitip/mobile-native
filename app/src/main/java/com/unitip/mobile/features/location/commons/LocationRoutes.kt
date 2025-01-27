package com.unitip.mobile.features.location.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class LocationRoutes {
    @Serializable
    object PickLocation
}