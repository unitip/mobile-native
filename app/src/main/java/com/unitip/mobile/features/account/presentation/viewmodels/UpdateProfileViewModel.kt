package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AccountRepository
import com.unitip.mobile.shared.commons.constants.GenderConstant
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.account.presentation.states.UpdateProfileState as State

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()
    val session get() = sessionManager.read()

    fun save(
        name: String,
        gender: GenderConstant
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        accountRepository
            .updateProfile(
                name = name,
                gender = gender,
            )
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
    }
}