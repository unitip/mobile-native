package com.unitip.mobile.features.account.presentation.states

data class OrderHistoryState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(
            val orders: List<Any>
        ) : Detail

        data class Failure(
            val message: String
        ) : Detail
    }
}
