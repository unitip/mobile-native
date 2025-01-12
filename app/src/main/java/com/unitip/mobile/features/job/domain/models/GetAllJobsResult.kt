package com.unitip.mobile.features.job.domain.models

data class GetAllJobsResult(
    val jobs: List<Job>,
    val hasNext: Boolean
)

