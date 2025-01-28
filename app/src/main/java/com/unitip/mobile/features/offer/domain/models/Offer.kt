package com.unitip.mobile.features.offer.domain.models

import com.google.gson.annotations.SerializedName
import com.unitip.mobile.features.job.domain.models.Applicant
import com.unitip.mobile.features.job.domain.models.JobCustomer

data class Offer(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: Number = 0,
    val type: String = "",
    val pickupArea: String = "",
    val deliveryArea: String = "",
    val availableUntil: String = "",
    val offerStatus: String = "",
    val freelancer: OfferFreelancer = OfferFreelancer(),
    val createdAt: String = "",
    val updatedAt: String = "",
    val totalApplicants: Int = 0,
    val applicants: List<Any> = emptyList()
)
