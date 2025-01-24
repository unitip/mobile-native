package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.caches.JobCache
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val jobRepository: JobRepository,
    private val jobCache: JobCache
) : ViewModel() {
    private val session = sessionManager.read()

    private val _uiState = MutableStateFlow(JobsState())
    val uiState get() = _uiState
//    val jobs get() = jobCache.content

    init {
        _uiState.update { it.copy(session = session) }

        viewModelScope.launch {
            jobCache.content.collectLatest { value ->
                _uiState.update { it.copy(jobs = value) }
            }
        }

        if (uiState.value.detail !is JobsState.Detail.Success)
            fetchJobs()
    }

    fun expandJob(jobId: String) = _uiState.update {
        it.copy(expandedJobId = if (it.expandedJobId == jobId) "" else jobId)
    }

    fun refreshJobs() = fetchJobs()

    private fun fetchJobs() = viewModelScope.launch {
        _uiState.update { it.copy(detail = JobsState.Detail.Loading) }
        jobRepository.getAll().fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = JobsState.Detail.Failure(message = left.message)
                    )
                }
            },
            ifRight = { right ->
                jobCache.setJobs(jobs = right.jobs)
                _uiState.update {
                    it.copy(
                        detail = JobsState.Detail.Success,
//                        result = right
                    )
                }
            }
        )
    }
}