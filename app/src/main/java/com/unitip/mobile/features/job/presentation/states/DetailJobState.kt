package com.unitip.mobile.features.job.presentation.states

import com.unitip.mobile.features.job.domain.models.JobApplicationModel
import com.unitip.mobile.features.job.domain.models.JobModel
import com.unitip.mobile.shared.domain.models.Session

data class DetailJobState(
    val session: Session = Session(),
    val jobModel: JobModel.Detail = JobModel.Detail(),
    val detail: Detail = Detail.Initial,
    val getAllApplicationsDetail: GetAllApplicationsDetail = GetAllApplicationsDetail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(
            val message: String
        ) : Detail
    }

    sealed interface GetAllApplicationsDetail {
        data object Initial : GetAllApplicationsDetail
        data object Loading : GetAllApplicationsDetail
        data class Success(
            val applications: List<JobApplicationModel>
        ) : GetAllApplicationsDetail

        data class Failure(
            val message: String
        ) : GetAllApplicationsDetail
    }
}
