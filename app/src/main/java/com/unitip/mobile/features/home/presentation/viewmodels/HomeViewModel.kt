package com.unitip.mobile.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.features.home.presentation.states.HomeState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    sessionManager: SessionManager
) : ViewModel() {
    private val session = sessionManager.read()

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(session = session) }
    }
}