package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyJobViewModel @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ApplyJobState())
    val uiState get() = _uiState.asStateFlow()

    fun apply(
        jobId: String,
        type: String,
        price: Int
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = ApplyJobState.Detail.Loading) }

        jobRepository.apply(jobId, price).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = ApplyJobState.Detail.Failure(message = left.message)
                    )
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(detail = ApplyJobState.Detail.Success)
                }
            }
        )
    }
}