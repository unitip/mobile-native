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
    private val driverJobRepository: DriverJobRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState

    init {
        if (uiState.value.detail !is State.Detail.Success)
            getAllJobs()
    }

    fun resetState() = _uiState.update {
        it.copy(detail = State.Detail.Initial)
    }

    fun resetApplyState() = _uiState.update {
        it.copy(applyDetail = State.ApplyDetail.Initial)
    }

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
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Success(jobs = right)
                    )
                }
            }
    }

    fun apply(
        jobId: String,
        bidPrice: Int
    ) = viewModelScope.launch {
        _uiState.update { it.copy(applyDetail = State.ApplyDetail.Loading(jobId = jobId)) }
        driverJobRepository
            .createApplication(
                jobId = jobId,
                bidPrice = bidPrice,
                bidNote = ""
            )
            .onLeft { left ->
                _uiState.update {
                    it.copy(applyDetail = State.ApplyDetail.Failure(message = left.message))
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(applyDetail = State.ApplyDetail.Success)
                }
            }
    }
}