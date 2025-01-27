package com.unitip.mobile.features.offer.domain.models

data class GetAllOffersResult(
    val offers: List<Offer> = emptyList(),
    val hasNext: Boolean = false
)
