package com.unitip.mobile.features.setting.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.setting.data.repositories.AccountRepository
import com.unitip.mobile.features.setting.presentation.states.EditPasswordState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditPasswordState())
    val uiState get() = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(session = sessionManager.read()) }
    }

    fun edit(password: String, confirmPassword: String) = viewModelScope.launch {
        viewModelScope.launch {
            _uiState.update {
                it.copy(editDetail = EditPasswordState.EditDetail.Loading)
            }

            if (password == confirmPassword)
                accountRepository.editPassword(
                    password = password
                ).fold(
                    ifLeft = { left ->
                        _uiState.update {
                            it.copy(
                                editDetail = EditPasswordState.EditDetail.Failure(
                                    message = left.message
                                )
                            )
                        }
                    },
                    ifRight = { right -> _uiState.update { it.copy(editDetail = EditPasswordState.EditDetail.Success) } }

                )
            else {
                _uiState.update { it.copy(editDetail = EditPasswordState.EditDetail.Failure(message = "Password tidak sama")) }
            }
        }
    }
}