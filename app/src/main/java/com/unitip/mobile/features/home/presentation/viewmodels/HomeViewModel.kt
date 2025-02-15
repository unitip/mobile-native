package com.unitip.mobile.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    val session get() = sessionManager.read()
}