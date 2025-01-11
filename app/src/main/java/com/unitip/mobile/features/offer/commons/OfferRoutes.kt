package com.unitip.mobile.features.offer.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class OfferRoutes {
    @Serializable
    object Create
}