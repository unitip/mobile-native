package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.data.models.GetJobResult

data class DetailJobState(
    val detail: DetailJobStateDetail = DetailJobStateDetail.Initial
)

sealed interface DetailJobStateDetail {
    data object Initial : DetailJobStateDetail
    data object Loading : DetailJobStateDetail
    data class Success(val result: GetJobResult) : DetailJobStateDetail
    data class Failure(val message: String) : DetailJobStateDetail
}
