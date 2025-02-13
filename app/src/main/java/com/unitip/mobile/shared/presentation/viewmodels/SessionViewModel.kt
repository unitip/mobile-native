package com.unitip.mobile.shared.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Deprecated("ganti langsung read dari local saja, karena susah untuk implementasi shared view model")
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _session = MutableStateFlow(Session())
    val session get() = _session.asStateFlow()

    init {
        refreshSession()
    }

    fun refreshSession() = _session.update {
        sessionManager.read()
    }
}