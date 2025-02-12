package com.unitip.mobile.features.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.account.data.repositories.AccountRepository
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.account.presentation.states.EditProfileState as State

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    private val session = sessionManager.read()

    init {
        _uiState.update { it.copy(session = session) }
    }

    fun edit(
        name: String,
        gender: String
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        accountRepository
            .editProfile(
                name = name,
                gender = gender,
            )
            .onLeft { }
            .onRight { }
    }
}