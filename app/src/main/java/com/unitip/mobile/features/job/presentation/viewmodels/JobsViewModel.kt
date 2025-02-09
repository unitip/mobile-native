package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.managers.JobManager
import com.unitip.mobile.features.job.data.repositories.DriverJobRepository
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.JobsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
//    sessionManager: SessionManager,
    private val jobRepository: JobRepository,
    private val jobManager: JobManager,
    private val driverJobRepository: DriverJobRepository
) : ViewModel() {
//    private val session = sessionManager.read()

    private val _uiState = MutableStateFlow(JobsState())
    val uiState get() = _uiState

    init {
//        _uiState.update { it.copy(session = session) }

        if (uiState.value.detail !is JobsState.Detail.Success)
            fetchJobs()
    }

//    fun expandJob(jobId: String) = _uiState.update {
//        it.copy(expandedJobId = if (it.expandedJobId == jobId) "" else jobId)
//    }

    fun refreshJobs() = fetchJobs()

    private fun fetchJobs() = viewModelScope.launch {
        _uiState.update { it.copy(detail = JobsState.Detail.Loading) }
        driverJobRepository.getAll()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = JobsState.Detail.Failure(message = left.message)
                    )
                }
            }
            .onRight { right ->
                jobManager.set(right)
                _uiState.update {
                    it.copy(
                        detail = JobsState.Detail.Success(jobs = right)
                    )
                }
            }
    }
}