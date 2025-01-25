package com.unitip.mobile.features.job.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class JobRoutes {
    @Serializable
    object Create

    @Serializable
    data class Detail(
        val jobId: String,
        val jobType: JobConstants.Type
    )

    @Serializable
    data class Apply(
        val jobId: String,
        val jobType: JobConstants.Type
    )
}