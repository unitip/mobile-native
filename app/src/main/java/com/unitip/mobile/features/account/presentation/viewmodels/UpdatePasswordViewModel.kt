package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.account.presentation.states.UpdatePasswordState as State

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    fun resetState() = _uiState.update { it.copy(detail = State.Detail.Initial) }

    fun save(
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }

        when (password.isNotBlank() && password == confirmPassword) {
            true -> accountRepository
                .updatePassword(password = password)
                .onLeft { left ->
                    _uiState.update {
                        it.copy(detail = State.Detail.Failure(message = left.message))
                    }
                }
                .onRight {
                    _uiState.update {
                        it.copy(detail = State.Detail.Success)
                    }
                }

            else -> _uiState.update {
                it.copy(
                    detail = State.Detail.Failure(message = "Konfirmasi kata sandi tidak valid!")
                )
            }
        }
    }
}