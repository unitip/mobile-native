package com.unitip.mobile.features.job.domain.models

sealed class JobModel {
    data class ListItem(
        val id: String,
        val title: String,
        val note: String,
        val service: String,
        val pickupLocation: String,
        val destinationLocation: String,
        val createdAt: String,
        val updatedAt: String,
        val customer: Customer
    ) {
        data class Customer(
            val name: String
        )
    }

    data class Detail(
        val id: String = "",
        val title: String = "",
        val destinationLocation: String = "",
        val destinationLatitude: Double? = null,
        val destinationLongitude: Double? = null,
        val note: String = "",
        val service: String = "",
        val pickupLocation: String = "",
        val pickupLatitude: Double? = null,
        val pickupLongitude: Double? = null,
        val createdAt: String = "",
        val updatedAt: String = "",
        val customer: Customer = Customer()
    ) {
        data class Customer(
            val id: String = "",
            val name: String = ""
        )
    }
}