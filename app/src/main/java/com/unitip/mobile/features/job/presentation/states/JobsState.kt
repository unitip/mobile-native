package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.data.models.GetAllJobsResult

data class JobsState(
    val detail: JobsStateDetail = JobsStateDetail.Initial,
)

sealed interface JobsStateDetail {
    data object Initial : JobsStateDetail
    data object Loading : JobsStateDetail
    data class Success(val result: GetAllJobsResult) : JobsStateDetail
    data class Failure(val message: String) : JobsStateDetail
}
