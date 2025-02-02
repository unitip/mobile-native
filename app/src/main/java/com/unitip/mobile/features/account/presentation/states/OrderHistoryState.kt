package com.unitip.mobile.features.account.presentation.states

import com.unitip.mobile.features.account.domain.models.Order

data class OrderHistoryState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(
            val orders: List<Order>
        ) : Detail

        data class Failure(
            val message: String
        ) : Detail
    }
}
