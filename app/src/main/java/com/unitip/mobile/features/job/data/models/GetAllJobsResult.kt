package com.unitip.mobile.features.job.data.models

data class GetAllJobsResult(
    val jobs: List<Job>,
    val hasNext: Boolean
)

