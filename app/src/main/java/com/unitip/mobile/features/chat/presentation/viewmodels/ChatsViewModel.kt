package com.unitip.mobile.features.chat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.presentation.states.ChatsState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatsState())
    val uiState get() = _uiState.asStateFlow()

    init {
        // read session
        _uiState.update { it.copy(session = sessionManager.read()) }

        getAllRooms()
    }

    fun getAllRooms() = viewModelScope.launch {
        _uiState.update { it.copy(detail = ChatsState.Detail.Loading) }
        chatRepository.getAllRooms().fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(detail = ChatsState.Detail.Failure(message = left.message))
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        rooms = right,
                        detail = ChatsState.Detail.Success
                    )
                }
            }
        )
    }
}