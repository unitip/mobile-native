package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.data.models.GetAllJobsResult
import com.unitip.mobile.shared.domain.models.Session

data class JobsState(
    val session: Session? = null,
    val detail: Detail = Detail.Initial,
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(val result: GetAllJobsResult) : Detail
        data class Failure(val message: String) : Detail
    }
}
