package com.unitip.mobile.features.job.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.data.repositories.SingleJobRepository
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
    private val singleJobRepository: SingleJobRepository
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
        singleJobRepository.get(
            jobId = parameters.jobId,
            service = parameters.service
        ).fold(
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
                        job = right
                    )
                }
            }
        )
    }

    fun approveApplicant(jobId: String, applicantId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(approveDetail = DetailJobState.ApproveDetail.Loading) }
            Log.d("detailJobScreen", "on click kepanggil")

            singleJobRepository.approve(jobId = jobId, applicantId = applicantId).fold(
                ifLeft = { left ->
                    _uiState.update {
                        it.copy(approveDetail = DetailJobState.ApproveDetail.Failure(message = left.message))
                    }
                    Log.d("detailJobScreen", "kiri: ${left.message}")

                },
                ifRight = {
                    _uiState.update {
                        it.copy(approveDetail = DetailJobState.ApproveDetail.Success)
                    }
                    Log.d("detailJobScreen", "kanan")

                }
            )
        }

    }

}