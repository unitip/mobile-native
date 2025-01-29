package com.unitip.mobile.features.offer.presentation.states

import com.unitip.mobile.features.offer.domain.models.GetAllOffersResult
import com.unitip.mobile.shared.domain.models.Session

data class OfferState(
    val expandOfferId: String = "",
    val result: GetAllOffersResult = GetAllOffersResult(),
    val session: Session? = null,
    val detail: Detail = Detail.Initial,
    val currentPage: Int = 1
){
    sealed interface Detail{
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}
