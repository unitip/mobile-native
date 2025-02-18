package com.unitip.mobile.features.social.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.unitip.mobile.features.social.presentation.state.CreateActivityState as State

@HiltViewModel
class CreateActivityViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    fun upload(
        content: String
    ) {
    }
}