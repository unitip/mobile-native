package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.DetailJobState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailJobViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    private val jobRepository: JobRepository
) : ViewModel() {
    companion object {
        private const val TAG = "DetailJobViewModel"
    }

    private val session = sessionManager.read()
    private val parameters = savedStateHandle.toRoute<JobRoutes.Detail>()

    private val _uiState = MutableStateFlow(DetailJobState())
    val uiState get() = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(session = session) }

        if (uiState.value.detail !is DetailJobState.Detail.Success)
            fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.update { it.copy(detail = DetailJobState.Detail.Loading) }
        jobRepository.get(jobId = parameters.jobId).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = DetailJobState.Detail.Failure(message = left.message)
                    )
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        detail = DetailJobState.Detail.Success,
                        jobModel = right
                    )
                }
            }
        )
    }

//    fun apply() = viewModelScope.launch {
//        _uiState.update { it.copy(applyDetail = DetailJobState.ApplyDetail.Loading) }
//        jobRepository.apply(jobId = parameters.jobId)
//            .onLeft { left ->
//                _uiState.update {
//                    it.copy(
//                        applyDetail = DetailJobState.ApplyDetail.Failure(
//                            message = left.message
//                        )
//                    )
//                }
//            }
//            .onRight {
//                _uiState.update {
//                    it.copy(applyDetail = DetailJobState.ApplyDetail.Success)
//                }
//            }
//    }
}