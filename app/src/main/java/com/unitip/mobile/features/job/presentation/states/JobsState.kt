package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.JobModel

data class JobsState(
//    val expandedJobId: String = "",
//    val jobs: List<JobModel.ListItem> = emptyList(),
//    val session: Session? = null,
    val detail: Detail = Detail.Initial,
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(
            val jobs: List<JobModel.ListItem>
        ) : Detail

        data class Failure(
            val message: String
        ) : Detail
    }
}
