package com.unitip.mobile.features.job.presentation.states

data class CreateJobState(
    val detail: CreateJobStateDetail = CreateJobStateDetail.Initial
)

sealed interface CreateJobStateDetail {
    data object Initial : CreateJobStateDetail
    data object Loading : CreateJobStateDetail
    data object Success : CreateJobStateDetail
    data class Failure(val message: String) : CreateJobStateDetail
}
