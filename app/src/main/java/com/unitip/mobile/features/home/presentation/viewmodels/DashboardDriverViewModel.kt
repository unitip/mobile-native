package com.unitip.mobile.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.home.data.repositories.AccountDriverRepository
import com.unitip.mobile.features.home.data.repositories.DriverOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.home.presentation.states.DashboardDriverState as State

@HiltViewModel
class DashboardDriverViewModel @Inject constructor(
    private val driverOrderRepository: DriverOrderRepository,
    private val accountDriverRepository: AccountDriverRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    init {
        if (uiState.value.detail !is State.Detail.Success)
            getDashboard()
    }

    fun getDashboard() = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        accountDriverRepository
            .getDashboard()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight { right ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Success(
                            data = right
                        )
                    )
                }
            }
    }
}