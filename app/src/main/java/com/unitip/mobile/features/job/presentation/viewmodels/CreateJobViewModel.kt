package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.CustomerJobRepository
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val customerJobRepository: CustomerJobRepository
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
        destinationLocation: String,
        note: String,
        service: String,
        pickupLocation: String,
        expectedPrice: Int
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = CreateJobState.Detail.Loading) }
        customerJobRepository
            .create(
                title = title,
                destinationLocation = destinationLocation,
                note = note,
                service = service,
                pickupLocation = pickupLocation,
                expectedPrice = expectedPrice
            )
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = CreateJobState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(detail = CreateJobState.Detail.Success)
                }
            }
    }
}