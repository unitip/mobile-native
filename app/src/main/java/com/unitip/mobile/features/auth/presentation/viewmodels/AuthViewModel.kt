package com.unitip.mobile.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.auth.data.repositories.AuthRepository
import com.unitip.mobile.features.auth.presentation.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState get() = _uiState.asStateFlow()

    fun resetState() = _uiState.update {
        it.copy(detail = AuthState.Detail.Initial)
    }

    fun switchAuthMode() = _uiState.update {
        it.copy(isLogin = !it.isLogin)
    }

    fun login(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = AuthState.Detail.Loading) }
        authRepository.login(email = email, password = password).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(detail = AuthState.Detail.Failure(message = left.message))
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        detail = when (right.needRole) {
                            true -> AuthState.Detail.SuccessWithPickRole(
                                roles = right.roles
                            )

                            false -> AuthState.Detail.Success
                        }
                    )
                }
            }
        )
    }

    fun register() {

    }
}