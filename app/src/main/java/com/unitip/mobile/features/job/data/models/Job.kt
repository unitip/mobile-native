package com.unitip.mobile.features.job.data.models

data class Job(
    val id: String,
    val title: String,
    val note: String,
    val pickupLocation: String,
    val destination: String,
    val type: String,
    val customer: JobCustomer
)
