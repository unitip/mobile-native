package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AuthRepository
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.account.presentation.states.ProfileState as State

@HiltViewModel
class ProfileViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    private val session = sessionManager.read()

    init {
        _uiState.update { it.copy(session = session) }
    }

    fun logout() = viewModelScope.launch {
        _uiState.update { it.copy(logoutDetail = State.LogoutDetail.Loading) }
        authRepository
            .logout()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        logoutDetail = State.LogoutDetail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(
                        logoutDetail = State.LogoutDetail.Success
                    )
                }
            }
    }
}