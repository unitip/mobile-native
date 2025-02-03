package com.unitip.mobile.features.offer.domain.models

data class OfferDetail(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: Number = 0,
    val type: String = "",
    val pickupArea: String = "",
    val destinationArea: String = "",
    val availableUntil: String = "",
    val offerStatus: String = "",
    val freelancer: OfferFreelancer = OfferFreelancer(),
    val createdAt: String = "",
    val updatedAt: String = "",
    val applicantsCount: Int = 0,
    val hasApplied: Boolean = false,
    val maxParticipants: Int = 1
)
