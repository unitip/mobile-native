package com.unitip.mobile.features.home.presentation.states

import com.unitip.mobile.features.home.domain.models.Order

data class DashboardDriverState(
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
