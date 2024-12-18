package com.unitip.mobile.features.setting.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.setting.data.repositories.AuthRepository
import com.unitip.mobile.features.setting.presentation.states.ProfileDetail
import com.unitip.mobile.features.setting.presentation.states.ProfileState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    sessionManager: SessionManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState get() = _uiState

    init {
        val session = sessionManager.read()
        if (session != null)
            _uiState.value = with(uiState.value) {
                copy(
                    name = session.name,
                    email = session.email,
                    token = session.token
                )
            }

    }

    fun resetState() {
        _uiState.value = ProfileState(
            detail = ProfileDetail.Initial
        )
    }

    fun logout() {
        _uiState.value = with(uiState.value) {
            copy(detail = ProfileDetail.Loading)
        }

        viewModelScope.launch {
            authRepository.logout().fold(
                ifLeft = {
                    _uiState.value = with(uiState.value) {
                        copy(
                            detail = ProfileDetail.Failure(
                                message = it.message,
                                code = it.code,
                            )
                        )
                    }
                },
                ifRight = {
                    _uiState.value = with(uiState.value) {
                        copy(detail = ProfileDetail.Success)
                    }
                }
            )
        }
    }
}