package com.unitip.mobile.features.job.data.managers

import com.unitip.mobile.features.job.domain.models.JobModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobManager @Inject constructor() {
    private var jobs = emptyList<JobModel.ListItem>()

    fun set(jobs: List<JobModel.ListItem>) {
        this.jobs = jobs
    }

    fun getAll(): List<JobModel.ListItem> =
        jobs

    fun get(id: String): JobModel.ListItem? =
        jobs.find { it.id == id }

    fun update(job: JobModel.ListItem) {
        this.jobs = jobs.map {
            when (it.id == job.id) {
                true -> job
                else -> it
            }
        }
    }
}