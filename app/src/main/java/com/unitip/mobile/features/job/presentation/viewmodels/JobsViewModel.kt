package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.features.job.presentation.states.JobsStateDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(JobsState())
    val uiState get() = _uiState

    init {
        fetchJobs()
    }

    fun refreshJobs() = fetchJobs()

    private fun fetchJobs() {
        viewModelScope.launch {
            _uiState.value = with(uiState.value) {
                copy(detail = JobsStateDetail.Loading)
            }

            jobRepository.getAll().fold(
                ifLeft = {
                    _uiState.value = with(uiState.value) {
                        copy(detail = JobsStateDetail.Failure(message = it.message))
                    }
                },
                ifRight = {
                    _uiState.value = with(uiState.value) {
                        copy(detail = JobsStateDetail.Success(result = it))
                    }
                }
            )
        }
    }
}