package com.unitip.mobile.features.offer.domain.models

import com.unitip.mobile.features.job.domain.models.Job

data class GetAllOffersResult(
    val offers: List<Offer> = emptyList(),
    val hasNext: Boolean = false
)
