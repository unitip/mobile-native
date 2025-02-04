package com.unitip.mobile.features.job.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class JobRoutes {
    @Serializable
    object Create

    @Serializable
    data class Detail(
        val jobId: String
    )

    @Serializable
    data class DetailOrderDriver(
        val orderId: String
    )

    @Serializable
    data class DetailOrderCustomer(
        val orderId: String
    )

    @Serializable
    data class Apply(
        val jobId: String
    )
}