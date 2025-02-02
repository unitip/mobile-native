package com.unitip.mobile.features.job.presentation.states

data class DetailOrderJobState(
    val cancelDetail: CancelDetail = CancelDetail.Initial
) {
    sealed interface CancelDetail {
        data object Initial : CancelDetail
        data object Loading : CancelDetail
        data object Success : CancelDetail
        data class Failure(
            val message: String
        ) : CancelDetail
    }
}
