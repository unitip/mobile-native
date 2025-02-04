package com.unitip.mobile.features.job.presentation.states

data class DetailOrderJobDriverState(
    val completeDetail: CompleteDetail = CompleteDetail.Initial,
    val cancelDetail: CancelDetail = CancelDetail.Initial
) {
    sealed interface CompleteDetail {
        data object Initial : CompleteDetail
        data object Loading : CompleteDetail
        data object Success : CompleteDetail
        data class Failure(
            val message: String
        ) : CompleteDetail
    }

    sealed interface CancelDetail {
        data object Initial : CancelDetail
        data object Loading : CancelDetail
        data object Success : CancelDetail
        data class Failure(
            val message: String
        ) : CancelDetail
    }
}
