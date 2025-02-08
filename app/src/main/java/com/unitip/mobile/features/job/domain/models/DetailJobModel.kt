package com.unitip.mobile.features.job.domain.models

sealed class DetailJobModel {
    data object General

    data class ForCustomer(
        val id: String,
        val title: String,
        val note: String,
        val applications: List<Application>
    ) {
        data class Application(
            val id: String,
            val bidPrice: Int,
            val bidNote: String,
            val driver: Driver
        ) {
            data class Driver(
                val name: String
            )
        }
    }

    data object ForDriver
}