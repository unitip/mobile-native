package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AccountRepository
import com.unitip.mobile.features.account.presentation.states.OrderHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderHistoryState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getOrderHistories()
    }

    fun getOrderHistories() = viewModelScope.launch {
        _uiState.update {
            it.copy(detail = OrderHistoryState.Detail.Loading)
        }

        accountRepository.getOrderHistories()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = OrderHistoryState.Detail.Failure(message = left.message)
                    )
                }
            }
            .onRight { right ->
                _uiState.update {
                    it.copy(
                        detail = OrderHistoryState.Detail.Success(orders = right)
                    )
                }
            }
    }
}