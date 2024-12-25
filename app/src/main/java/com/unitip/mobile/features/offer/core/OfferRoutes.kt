package com.unitip.mobile.features.offer.core

import kotlinx.serialization.Serializable

@Serializable
sealed class OfferRoutes {
    @Serializable
    object Create
}