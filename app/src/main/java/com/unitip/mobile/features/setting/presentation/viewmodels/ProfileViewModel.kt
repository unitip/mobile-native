package com.unitip.mobile.features.setting.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.features.setting.presentation.states.ProfileState
import com.unitip.mobile.shared.helper.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
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

    fun logout() {}
}