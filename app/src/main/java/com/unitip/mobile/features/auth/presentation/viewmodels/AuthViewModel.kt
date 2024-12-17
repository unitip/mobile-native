package com.unitip.mobile.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.auth.data.repositories.AuthRepository
import com.unitip.mobile.features.auth.presentation.states.AuthState
import com.unitip.mobile.features.auth.presentation.states.AuthStateDetail
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
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> get() = _uiState.asStateFlow()

    fun resetState() {
        _uiState.value = with(uiState.value) {
            copy(detail = AuthStateDetail.Initial)
        }
    }

    fun switchAuthMode() {
        _uiState.value = with(uiState.value) {
            copy(isLogin = !isLogin)
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        _uiState.value = with(uiState.value) {
            copy(detail = AuthStateDetail.Loading)
        }

        viewModelScope.launch {
            authRepository.login(email = email, password = password).fold(
                ifLeft = {
                    _uiState.value = with(uiState.value) {
                        copy(detail = AuthStateDetail.Failure(message = it.message))
                    }
                },
                ifRight = {
                    _uiState.value = with(uiState.value) {
                        copy(
                            detail = when (it.needRole) {
                                true -> AuthStateDetail.SuccessWithPickRole(roles = it.roles)
                                false -> AuthStateDetail.Success
                            }
                        )
                    }
                }
            )
        }
    }

    fun register() {

    }
}