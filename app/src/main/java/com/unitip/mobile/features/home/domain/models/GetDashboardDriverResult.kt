package com.unitip.mobile.features.home.domain.models

data class GetDashboardDriverResult(
    val applications: List<Application>,
    val jobs: List<Job>,
    val offers: List<Offer>
) {
    data class Offer(
        val id: String,
        val title: String,
        val price: Int,
        val description: String,
        val pickupArea: String,
        val destinationArea: String,
        val type: String,
        val availableUntil: String,
        val maxParticipants: Int,
        val applicants: List<Applicant>
    ) {
        data class Applicant(
            val id: String,
            val customerName: String,
            val pickupLocation: String,
            val destinationLocation: String,
            val status: String,
            val finalPrice: Int
        )
    }

    data class Job(
        val id: String,
        val customer: Customer
    ) {
        data class Customer(
            val name: String
        )
    }

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
