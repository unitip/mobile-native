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
import com.unitip.mobile.features.account.presentation.states.ChangeRoleState as State

@HiltViewModel
class ChangeRoleViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    init {
        if (_uiState.value.getDetail !is State.GetDetail.Success)
            getAllRoles()
    }

    fun getAllRoles() = viewModelScope.launch {
        _uiState.update { it.copy(getDetail = State.GetDetail.Loading) }
        accountRepository
            .getAllRoles()
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        getDetail = State.GetDetail.Failure(message = left.message)
                    )
                }
            }
            .onRight { right ->
                _uiState.update {
                    it.copy(
                        getDetail = State.GetDetail.Success(roles = right)
                    )
                }
            }
    }

    fun changeRole(role: String) = viewModelScope.launch {
        _uiState.update { it.copy(changeDetail = State.ChangeDetail.Loading) }
        accountRepository.changeRole(role).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        changeDetail = State.ChangeDetail.Failure(
                            message = left.message
                        )
                    )
                }
            }, ifRight = { right ->
                _uiState.update {
                    it.copy(
                        changeDetail = State.ChangeDetail.Success
                    )

                }
            })

    }
}