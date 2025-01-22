package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.MultiJobRepository
import com.unitip.mobile.features.job.data.repositories.SingleJobRepository
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val singleJobRepository: SingleJobRepository,
    private val multiJobRepository: MultiJobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateJobState())
    val uiState get() = _uiState

    fun resetState() {
        _uiState.value = with(uiState.value) {
            copy(detail = CreateJobState.Detail.Initial)
        }
    }

    fun create(
        title: String,
        note: String,
        pickupLocation: String,
        destination: String,
        service: String
    ) = viewModelScope.launch {
        _uiState.value = with(uiState.value) {
            copy(detail = CreateJobState.Detail.Loading)
        }

        when (service) {
            "antar-jemput" -> {
                singleJobRepository.create(
                    title = title,
                    note = note,
                    pickupLocation = pickupLocation,
                    destination = destination,
                    service = service
                ).fold(
                    ifLeft = {
                        _uiState.value = with(uiState.value) {
                            copy(detail = CreateJobState.Detail.Failure(message = it.message))
                        }
                    },
                    ifRight = {
                        _uiState.value = with(uiState.value) {
                            copy(detail = CreateJobState.Detail.Success)
                        }
                    }
                )
            }

            else -> {
                _uiState.update {
                    it.copy(
                        detail = CreateJobState.Detail.Failure(message = "Not implemented yet!")
                    )
                }
            }
        }
    }
}