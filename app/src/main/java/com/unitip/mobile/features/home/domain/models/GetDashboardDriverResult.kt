package com.unitip.mobile.features.home.domain.models

data class GetDashboardDriverResult(
    val applications: List<Application>,
    val jobs: List<Any>,
    val offers: List<Any>
) {
    data class Application(
        val id: String,
        val bidPrice: Int,
        val bidNote: String,
        val job: Job
    ) {
        data class Job(
            val id: String,
            val note: String,
            val expectedPrice: Int,
            val customer: Customer
        ) {
            data class Customer(
                val name: String
            )
        }
    }
}
