package com.unitip.mobile.features.offer.domain.models

data class Offer(
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
    val maxParticipants: Number = 0,
    val totalApplicants: Int = 0,
    val applicants: List<Any> = emptyList()
)
