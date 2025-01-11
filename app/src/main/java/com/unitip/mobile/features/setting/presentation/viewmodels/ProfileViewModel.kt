package com.unitip.mobile.features.setting.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.setting.data.repositories.AuthRepository
import com.unitip.mobile.features.setting.presentation.states.ProfileState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState get() = _uiState.asStateFlow()

    init {
        loadSession()
    }

    private fun loadSession() {
        val session = sessionManager.read()
        if (session != null) _uiState.update {
            it.copy(
                name = session.name,
                email = session.email,
                token = session.token,
                role = session.role
            )
        }
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