package com.unitip.mobile.shared.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.data.repositories.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    private val _session = MutableStateFlow<Session?>(null)
    val session get() = _session

    init {
        refresh()
    }

    fun refresh() {
        sessionRepository.read().onRight {
            _session.value = it
        }
    }
}