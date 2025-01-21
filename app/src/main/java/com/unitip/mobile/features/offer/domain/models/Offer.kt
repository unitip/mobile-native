package com.unitip.mobile.features.offer.domain.models

import com.google.gson.annotations.SerializedName
import com.unitip.mobile.features.job.domain.models.Applicant
import com.unitip.mobile.features.job.domain.models.JobCustomer
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse.Offer.Freelencer

data class Offer(
    val id:  String = "",
    val title: String = "",
    val description: String = "",
    val price: Number = 0,
    val type: String = "",
    val pickupArea: String = "",
    val deliveryArea: String = "",
    val availableUntil: String = "",
    val offerStatus: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val totalApplicants: Int = 0,
    val freelancer: OfferFreelancer = OfferFreelancer(),
    val applicants: List<Any> = emptyList()

)
