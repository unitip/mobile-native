package com.unitip.mobile.features.job.domain.models

data class JobApplicationModel(
    val id: String,
    val price: Int,
    val bidNote: String,
    val driver: Driver
) {
    data class Driver(
        val name: String
    )
}
