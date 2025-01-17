package com.unitip.mobile.features.chat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.presentation.states.ConversationState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConversationState())
    val uiState get() = _uiState.asStateFlow()

    fun sendMessage(
        toUserId: String,
        message: String
    ) = viewModelScope.launch {
        val uuid = UUID.randomUUID().toString()

        _uiState.update {
            it.copy(
                sendingMessageUUIDs = it.sendingMessageUUIDs + uuid,
                messages = it.messages + Message(
                    id = uuid,
                    message = message,
                    toUserId = toUserId,
                    // fromUserId = dari mana idnya???
                )
            )
        }
        chatRepository.sendMessage(
            toUserId = toUserId,
            id = uuid,
            message = message
        ).fold(
            ifLeft = {
                _uiState.update {
                    it.copy(failedMessageUUIDs = it.failedMessageUUIDs + uuid)
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(
                        sendingMessageUUIDs = it.sendingMessageUUIDs - uuid,
                    )
                }
            }
        )

    }

    fun getAllMessages(fromUserId: String) = viewModelScope.launch {
        _uiState.update { it.copy(detail = ConversationState.Detail.Loading) }
        chatRepository.getAllMessages(fromUserId = fromUserId).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = ConversationState.Detail.Failure(message = left.message)
                    )
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        messages = right,
                        detail = ConversationState.Detail.Success
                    )
                }
            }
        )
    }
}