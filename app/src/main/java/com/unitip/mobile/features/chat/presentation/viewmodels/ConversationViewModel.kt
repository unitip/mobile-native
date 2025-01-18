package com.unitip.mobile.features.chat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.data.repositories.RealtimeChatRepository
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeChat
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
    private val sessionManager: SessionManager,
    private val realtimeChatRepository: RealtimeChatRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConversationState())
    val uiState get() = _uiState.asStateFlow()

    fun resetRealtimeState() = _uiState.update {
        it.copy(realtimeDetail = ConversationState.RealtimeDetail.Initial)
    }

    fun openRealtimeConnection(
        otherUserId: String
    ) {
        val currentUserId = sessionManager.read()?.id ?: ""
        if (currentUserId.isNotEmpty() && otherUserId.isNotEmpty()) {
            realtimeChatRepository.listenMessageFromOther(
                object : RealtimeChat.MessageListener {
                    override fun onMessageReceived(message: Message) = _uiState.update {
                        it.copy(
                            messages = it.messages + message,
                            realtimeDetail = ConversationState.RealtimeDetail.NewMessage
                        )
                    }
                }
            )

            realtimeChatRepository.listenTypingStatusFromOther(
                object : RealtimeChat.TypingStatusListener {
                    override fun onTypingStatusReceived(isTyping: Boolean) = _uiState.update {
                        it.copy(isOtherUserTyping = isTyping)
                    }
                }
            )

            realtimeChatRepository.openConnection(
                currentUserId = currentUserId,
                otherUserId = otherUserId
            )
        }
    }

    fun sendMessage(
        toUserId: String,
        message: String
    ) = viewModelScope.launch {
        val uuid = UUID.randomUUID().toString()
        val fromUserId = sessionManager.read()?.id ?: ""
        val newMessage = Message(
            id = uuid,
            message = message,
            toUserId = toUserId,
            fromUserId = fromUserId
        )

        _uiState.update {
            it.copy(
                sendingMessageUUIDs = it.sendingMessageUUIDs + uuid,
                messages = it.messages + newMessage,
                realtimeDetail = ConversationState.RealtimeDetail.NewMessage
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
                realtimeChatRepository.notifyMessageToOther(message = newMessage)
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

    fun notifyTypingStatus(isTyping: Boolean) {
        _uiState.update { it.copy(isTyping = isTyping) }
        realtimeChatRepository.notifyTypingStatusToOther(isTyping = isTyping)
    }
}