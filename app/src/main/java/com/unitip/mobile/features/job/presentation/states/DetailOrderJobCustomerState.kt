package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.DetailJobModel

data class DetailOrderJobCustomerState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(
            val data: DetailJobModel.ForCustomer
        ) : Detail

        data class Failure(
            val message: String
        ) : Detail
    }
}
