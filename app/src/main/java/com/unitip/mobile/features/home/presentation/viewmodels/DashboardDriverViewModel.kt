package com.unitip.mobile.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.home.data.repositories.DriverOrderRepository
import com.unitip.mobile.features.home.presentation.states.DashboardDriverState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardDriverViewModel @Inject constructor(
    private val driverOrderRepository: DriverOrderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardDriverState())
    val uiState get() = _uiState.asStateFlow()

    init {
//        getAllOrders()
    }

    fun getAllOrders() = viewModelScope.launch {
        _uiState.update { it.copy(detail = DashboardDriverState.Detail.Loading) }
        driverOrderRepository.getAll()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = DashboardDriverState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight { right ->
                _uiState.update {
                    it.copy(
                        detail = DashboardDriverState.Detail.Success(
                            orders = right
                        )
                    )
                }
            }
    }
}