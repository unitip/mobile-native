package com.unitip.mobile.features.offer.presentation.states


data class CreateOfferState(
    val detail: Detail = Detail.Initial
){
    sealed interface Detail{
        data object Initial : Detail
        data object  Loading : Detail
        data object Success : Detail
        class Failure(val message: String): Detail
    }
}
