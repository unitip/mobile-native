package com.unitip.mobile.features.job.commons

import com.unitip.mobile.features.job.domain.models.JobService

object JobConstant {
    enum class Status {
        Initial, Ongoing, Done
    }

    enum class Service {
        AntarJemput, JasaTitip
    }

    @Deprecated("sudah tidak digunakan")
    enum class Type {
        SINGLE {
            override fun toString(): String = "single"
        },
        MULTI {
            override fun toString(): String = "multi"
        }
    }

    val services = listOf(
        JobService(
            title = "Antar jemput",
            value = Service.AntarJemput
        ),
        JobService(
            title = "Jasa titip",
            value = Service.JasaTitip
        )
    )
}