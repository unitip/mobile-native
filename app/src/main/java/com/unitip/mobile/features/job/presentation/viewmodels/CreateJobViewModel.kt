package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.CustomerJobRepository
import com.unitip.mobile.shared.commons.constants.ServiceTypeConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.job.presentation.states.CreateJobState as State

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val customerJobRepository: CustomerJobRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState

    fun resetState() = _uiState.update {
        it.copy(detail = State.Detail.Initial)
    }

    fun create(
        note: String,
        pickupLocation: String,
        destinationLocation: String,
        service: ServiceTypeConstant,
        expectedPrice: Int
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        customerJobRepository
            .create(
                note = note,
                pickupLocation = pickupLocation,
                destinationLocation = destinationLocation,
                service = service,
                expectedPrice = expectedPrice
            )
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(detail = State.Detail.Success)
                }
            }
    }
}