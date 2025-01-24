package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.Job
import com.unitip.mobile.shared.domain.models.Session

data class JobsState(
    val expandedJobId: String = "",
//    val result: GetAllJobsResult = GetAllJobsResult(),
    val jobs: List<Job> = emptyList(),
    val session: Session? = null,
    val detail: Detail = Detail.Initial,
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}
