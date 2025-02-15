package com.unitip.mobile.features.job.domain.models

import com.unitip.mobile.features.job.commons.JobConstant

sealed class DetailJobModel {
    data object General

    data class ForCustomer(
        val id: String,
        val note: String,
        val pickupLocation: String,
        val destinationLocation: String,
        val expectedPrice: Int,
        val price: Int,
        val service: JobConstant.Service,
        val status: JobConstant.Status,
        val applications: List<Application>,
        val driver: Driver?
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

        data class Driver(
            val id: String,
            val name: String
        )
    }

    data object ForDriver
}