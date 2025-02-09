package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.data.repositories.CustomerJobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.job.presentation.states.DetailOrderJobCustomerState as State

@HiltViewModel
class DetailOrderJobCustomerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val customerJobRepository: CustomerJobRepository
) : ViewModel() {
    private val parameters = savedStateHandle.toRoute<JobRoutes.DetailOrderCustomer>()
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    init {
        if (_uiState.value.detail !is State.Detail.Success)
            getData()
    }

    fun getData() = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }

        customerJobRepository
            .get(jobId = parameters.jobId)
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
                        detail = State.Detail.Success(data = right)
                    )
                }
            }
    }
}