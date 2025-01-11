package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.example.presentation.states.ExampleUsersState
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


    fun applyJob(jobId: String, type: String, price: Int) {
        viewModelScope.launch {
            _uiState.value = with(uiState.value) {
                copy(detail = ApplyJobState.Detail.Loading)
            }

            jobRepository.apply(jobId, price).fold(
                ifLeft = {
                    _uiState.value =
                        with(uiState.value) {
                            copy(detail = ApplyJobState.Detail.Failure(message = it.message))
                        }
                },
                ifRight = {
                    _uiState.value = with(uiState.value) {
                        copy(detail = ApplyJobState.Detail.Success)
                    }
                }
            )
        }
    }
}