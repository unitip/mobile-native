package com.unitip.mobile.features.job.commons

import com.unitip.mobile.features.job.domain.models.JobService

object JobConstants {
    val services = listOf<JobService>(
        JobService(
            title = "Antar jemput",
            value = "antar-jemput"
        ),
        JobService(
            title = "Jasa titip",
            value = "jasa-titip"
        )
    )
}