package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import com.unitip.mobile.features.job.presentation.states.Detail
import com.unitip.mobile.features.job.presentation.states.DetailJobState
import com.unitip.mobile.features.job.presentation.states.DetailJobStateDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailJobViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailJobState())
    
    val uiState get() = _uiState.asStateFlow()


    fun fetchData(jobId: String, type: String) = viewModelScope.launch {
        _uiState.value = with(uiState.value) {
            copy(detail = DetailJobStateDetail.Loading)
        }

        jobRepository.get(id = jobId, type = type).fold(
            ifLeft = {
                _uiState.value =
                    with(uiState.value) {
                        copy(
                            detail = DetailJobStateDetail.Failure(message = it.message)
                        )
                    }
            },
            ifRight = {
                _uiState.value = with(uiState.value) {
                    copy(detail = DetailJobStateDetail.Success(result = it))
                }
            }
        )
    }

}