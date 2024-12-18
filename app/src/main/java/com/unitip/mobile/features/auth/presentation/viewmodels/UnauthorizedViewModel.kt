package com.unitip.mobile.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnauthorizedViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {
    fun clearSession() = sessionManager.delete()
}