package com.unitip.mobile.features.offer.commons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class OfferRoutes {
    @Serializable
    object Create

    @Serializable
    @SerialName("offer/detail/{offerId}")
    data class Detail(
        val offerId: String
    )

    @Serializable
    @SerialName("offer/{offerId}/apply")
    data class ApplyOffer(
        val offerId: String
    )
}