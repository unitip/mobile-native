package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(JobsState())
    val uiState get() = _uiState

    init {
        // memuat authenticated user session untuk validasi role
        _uiState.update {
            it.copy(session = sessionManager.read())
        }

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
                _uiState.update {
                    it.copy(
                        detail = JobsState.Detail.Success,
                        result = right
                    )
                }
            }
        )
    }
}