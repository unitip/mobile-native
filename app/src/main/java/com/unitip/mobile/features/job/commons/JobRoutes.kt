package com.unitip.mobile.features.job.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class JobRoutes {
    @Serializable
    object Create

    @Serializable
    data class Detail(
        val id: String,
        val type: String
    )

    @Serializable
    data class Apply(
        val id: String,
        val type: String
    )
}