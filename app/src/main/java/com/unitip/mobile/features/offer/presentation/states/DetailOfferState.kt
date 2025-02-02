package com.unitip.mobile.features.offer.presentation.states

import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.shared.domain.models.Session

data class DetailOfferState(
    val session: Session = Session(),
    val detail: Detail = Detail.Initial,
    val offer: Offer = Offer()
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}