package com.unitip.mobile.features.job.data.models

data class GetJobResult(
    val job: Job,
    val applicants: List<Applicant>
)
