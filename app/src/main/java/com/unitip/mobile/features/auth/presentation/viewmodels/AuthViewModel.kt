package com.unitip.mobile.features.auth.presentation.viewmodels

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
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AuthUiState(
            status = UIStatus.Initial,
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun resetState() {
        _uiState.value = with(uiState.value) {
            copy(
                action = AuthUiAction.Initial,
                status = UIStatus.Initial,
            )
        }
    }

    fun switchAuthMode() {
        _uiState.value = with(uiState.value) {
            copy(
                action = AuthUiAction.SwitchAuthMode,
                isLogin = !isLogin
            )
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        _uiState.value = with(uiState.value) {
            copy(
                action = AuthUiAction.Login,
                status = UIStatus.Loading
            )
        }

        viewModelScope.launch {
            authRepository.login(email = email, password = password).fold(
                ifLeft = {
                    _uiState.value = with(uiState.value) {
                        copy(
                            action = AuthUiAction.Login,
                            status = UIStatus.Failure,
                            message = it.message
                        )
                    }
                },
                ifRight = {
                    _uiState.value = with(uiState.value) {
                        copy(
                            action = AuthUiAction.Login,
                            status = UIStatus.Success,
                            needRole = it.needRole,
                            roles = it.roles,
                        )
                    }
                }
            )
        }
    }

    fun register() {

    }
}