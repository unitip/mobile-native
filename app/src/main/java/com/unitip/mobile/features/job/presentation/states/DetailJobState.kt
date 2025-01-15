package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.Job

data class DetailJobState(
    val job: Job = Job(),
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}
