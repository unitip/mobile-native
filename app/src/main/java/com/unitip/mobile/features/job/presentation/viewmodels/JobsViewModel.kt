package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.DriverJobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.job.presentation.states.JobsState as State

@HiltViewModel
class JobsViewModel @Inject constructor(
//    sessionManager: SessionManager,
//    private val jobRepository: JobRepository,
//    private val jobManager: JobManager,
    private val driverJobRepository: DriverJobRepository
) : ViewModel() {
//    private val session = sessionManager.read()

    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState

    init {
//        _uiState.update { it.copy(session = session) }

        if (uiState.value.detail !is State.Detail.Success)
            getAllJobs()
    }

//    fun expandJob(jobId: String) = _uiState.update {
//        it.copy(expandedJobId = if (it.expandedJobId == jobId) "" else jobId)
//    }

    fun getAllJobs() = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        driverJobRepository.getAll()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Failure(message = left.message)
                    )
                }
            }
            .onRight { right ->
//                jobManager.set(right)
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Success(jobs = right)
                    )
                }
            }
    }
}