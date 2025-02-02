package com.unitip.mobile.features.job.presentation.states

@Deprecated("Sudah tidak digunakan")
data class ApplyJobState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}

