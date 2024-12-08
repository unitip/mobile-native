package com.unitip.mobile.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.unitip.mobile.features.auth.data.AuthRepository
import com.unitip.mobile.features.auth.states.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login() {

    }

    fun register() {

    }
}