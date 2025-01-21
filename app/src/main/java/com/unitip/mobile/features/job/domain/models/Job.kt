package com.unitip.mobile.features.job.domain.models

data class Job(
    val id: String = "",
    val title: String = "",
    val destination: String = "",
    val note: String = "",
    val service: String = "",
    val pickupLocation: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val totalApplicants: Int = 0,
    val customer: JobCustomer = JobCustomer(),
    val applicants: List<Applicant> = emptyList()
)
