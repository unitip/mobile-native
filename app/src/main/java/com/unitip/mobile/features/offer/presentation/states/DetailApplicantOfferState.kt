package com.unitip.mobile.features.offer.presentation.states

import com.unitip.mobile.features.offer.domain.models.DetailApplicantOffer
import com.unitip.mobile.shared.domain.models.Session

data class DetailApplicantOfferState(
    val session: Session = Session(),
    val detail: Detail = Detail.Initial,
    val applicant: DetailApplicantOffer = DetailApplicantOffer(),
    val updateStatus: UpdateStatus = UpdateStatus.Initial,
    val error: String? = null,
    val navigateToChat: Triple<String, String, String>? = null
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }

    sealed interface UpdateStatus {
        data object Initial : UpdateStatus
        data object Loading : UpdateStatus
        data object Success : UpdateStatus
        data class Failure(val message: String) : UpdateStatus
    }
}
