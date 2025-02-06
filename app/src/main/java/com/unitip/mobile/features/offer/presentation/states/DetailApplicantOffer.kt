package com.unitip.mobile.features.offer.presentation.states

import com.unitip.mobile.features.offer.domain.models.DetailApplicantOffer
import com.unitip.mobile.shared.domain.models.Session

data class DetailApplicantOfferState(
    val session: Session = Session(),
    val detail: Detail = Detail.Initial,
    val applicant: DetailApplicantOffer = DetailApplicantOffer()
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}