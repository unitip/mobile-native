package com.unitip.mobile.features.job.data.caches

import com.unitip.mobile.features.job.domain.models.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("Anti pattern")
@Singleton
class JobCache @Inject constructor() {
    private val _content = MutableStateFlow<List<Job>>(emptyList())
    val content get() = _content.asStateFlow()

    fun setJobs(jobs: List<Job>) =
        _content.update { jobs }

    fun getJob(jobId: String): Job? =
        content.value.find { it.id == jobId }

    fun updateJob(jobId: String, job: Job) = _content.update {
        it.map { item ->
            when (item.id == jobId) {
                true -> job
                else -> item
            }
        }
    }
}