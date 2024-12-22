package com.unitip.mobile.features.job.data.models

data class GetAllJobsResult(
    val jobs: List<Job>,
    val pageInfo: PageInfo
) {
    data class Job(
        val id: String,
        val title: String,
        val destination: String,
        val note: String,
        val type: String,
        val pickupLocation: String,
        val customer: Customer
    ) {
        data class Customer(
            val name: String
        )
    }

    data class PageInfo(
        val count: Int,
        val page: Int,
        val totalPages: Int
    )
}

