package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AccountRepository
import com.unitip.mobile.features.account.presentation.states.ChangeRoleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeRoleViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeRoleState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getRole()
    }

    fun getRole() = viewModelScope.launch {
        _uiState.update { it.copy(detail = ChangeRoleState.Detail.Loading) }
        accountRepository.getRoles().fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = ChangeRoleState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        detail = ChangeRoleState.Detail.Success
                            (roles = right)
                    )
                }
            })

    }
}