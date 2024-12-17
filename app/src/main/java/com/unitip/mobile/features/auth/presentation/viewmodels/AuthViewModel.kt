package com.unitip.mobile.features.auth.presentation.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.core.ui.UIStatus
import com.unitip.mobile.features.auth.data.repositories.AuthRepository
import com.unitip.mobile.features.auth.presentation.states.AuthUiAction
import com.unitip.mobile.features.auth.presentation.states.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val application: Application,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AuthUiState(
            status = UIStatus.Initial,
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String,
    ) {
        _uiState.value = _uiState.value.copy(
            status = UIStatus.Loading,
            action = AuthUiAction.Login,
        )

        viewModelScope.launch {
            val result = authRepository.login(email = email, password = password)
            result.fold(
                ifLeft = {
                    Toast.makeText(application, it.message, Toast.LENGTH_SHORT).show()
                },
                ifRight = {
                    Toast.makeText(application, "success", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    fun register() {

    }
}