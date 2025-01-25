package com.unitip.mobile.features.job.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class JobRoutes {
    @Serializable
    object Create

    @Serializable
    data class Detail(
        val jobId: String,
        val type: JobConstants.Type
    )

    @Serializable
    data class Apply(
        val id: String,
        val type: String
    )
}