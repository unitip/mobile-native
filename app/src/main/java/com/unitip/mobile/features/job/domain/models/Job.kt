package com.unitip.mobile.features.job.domain.models

@Deprecated(message = "Gunakan model baru yang lebih diperjelas untuk per screen")
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
