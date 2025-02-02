package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.features.account.presentation.states.OrderHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderHistoryState())
    val uiState get() = _uiState.asStateFlow()
}