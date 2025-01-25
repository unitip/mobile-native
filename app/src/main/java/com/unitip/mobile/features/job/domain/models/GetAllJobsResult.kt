package com.unitip.mobile.features.job.domain.models

data class GetAllJobsResult(
    val jobs: List<JobV2.List> = emptyList(),
    val hasNext: Boolean = false
)

