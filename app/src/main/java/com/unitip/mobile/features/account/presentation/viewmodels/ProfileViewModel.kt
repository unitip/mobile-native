package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AuthRepository
import com.unitip.mobile.features.account.presentation.states.ProfileState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState get() = _uiState.asStateFlow()

    private val session = sessionManager.read()

    init {
        _uiState.update { it.copy(session = session) }
    }

    fun logout() = viewModelScope.launch {
        _uiState.update { it.copy(logoutDetail = ProfileState.LogoutDetail.Loading) }
        authRepository.logout().fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        logoutDetail = ProfileState.LogoutDetail.Failure(
                            message = left.message,
                            code = left.code
                        )
                    )
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(
                        logoutDetail = ProfileState.LogoutDetail.Success
                    )
                }
            }
        )
    }
}