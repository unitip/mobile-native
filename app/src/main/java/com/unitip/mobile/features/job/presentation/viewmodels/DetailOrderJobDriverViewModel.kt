package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.DetailOrderJobDriverState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderJobDriverViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jobRepository: JobRepository
) : ViewModel() {
    private val parameters = savedStateHandle.toRoute<JobRoutes.DetailOrderDriver>()
    private val _uiState = MutableStateFlow(DetailOrderJobDriverState())
    val uiState get() = _uiState.asStateFlow()

    fun complete() = viewModelScope.launch {
        _uiState.update {
            it.copy(completeDetail = DetailOrderJobDriverState.CompleteDetail.Loading)
        }

        jobRepository.complete(jobId = parameters.orderId)
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        completeDetail = DetailOrderJobDriverState.CompleteDetail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(
                        completeDetail = DetailOrderJobDriverState.CompleteDetail.Success
                    )
                }
            }
    }

    fun cancel() = viewModelScope.launch {
        _uiState.update {
            it.copy(cancelDetail = DetailOrderJobDriverState.CancelDetail.Loading)
        }

        jobRepository.cancelApplication(jobId = parameters.orderId)
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        cancelDetail = DetailOrderJobDriverState.CancelDetail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(
                        cancelDetail = DetailOrderJobDriverState.CancelDetail.Success
                    )
                }
            }
    }
}