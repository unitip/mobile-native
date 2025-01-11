package com.unitip.mobile.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.auth.data.repositories.AuthRepository
import com.unitip.mobile.features.auth.presentation.states.PickRoleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickRoleViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PickRoleState())
    val uiState get() = _uiState.asStateFlow()

    fun login(email: String, password: String, role: String) = viewModelScope.launch {
        _uiState.update { it.copy(detail = PickRoleState.Detail.Loading) }
        authRepository.login(
            email = email,
            password = password,
            role = role
        ).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = PickRoleState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(detail = PickRoleState.Detail.Success)
                }
            }
        )
    }
}