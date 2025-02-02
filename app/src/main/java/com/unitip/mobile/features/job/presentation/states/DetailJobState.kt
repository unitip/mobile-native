package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.JobModel
import com.unitip.mobile.shared.domain.models.Session

data class DetailJobState(
    val session: Session = Session(),
    val jobModel: JobModel.Detail = JobModel.Detail(),
    val detail: Detail = Detail.Initial,
    val applyDetail: ApplyDetail = ApplyDetail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }

    sealed interface ApplyDetail {
        data object Initial : ApplyDetail
        data object Loading : ApplyDetail
        data object Success : ApplyDetail
        data class Failure(val message: String) : ApplyDetail
    }
}
