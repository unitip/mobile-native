package com.unitip.mobile.features.chat.presentation.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.chat.commons.ChatRoutes
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.data.repositories.RealtimeConversationRepository
import com.unitip.mobile.features.chat.data.repositories.RealtimeRoomRepository
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeChat
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.domain.models.OtherUser
import com.unitip.mobile.features.chat.domain.models.ReadCheckpoint
import com.unitip.mobile.features.chat.domain.models.Room
import com.unitip.mobile.features.chat.presentation.states.ConversationState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    private val chatRepository: ChatRepository,
    private val realtimeConversationRepository: RealtimeConversationRepository,
    private val realtimeRoomRepository: RealtimeRoomRepository
) : ViewModel() {
    companion object {
        private const val TAG = "ConversationViewModel"
    }

    private val _uiState = MutableStateFlow(ConversationState())
    val uiState get() = _uiState.asStateFlow()

    private val session = sessionManager.read()
    private val parameters = savedStateHandle.toRoute<ChatRoutes.Conversation>()

    init {
        // read session dari session manager
        _uiState.update { it.copy(session = session) }

        getAllMessages()
        openRealtimeConnection()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d(TAG, "onCleared: unsubscribe from topics")
        realtimeConversationRepository.unsubscribeFromTopics()
    }

    fun resetRealtimeState() = _uiState.update {
        it.copy(realtimeDetail = ConversationState.RealtimeDetail.Initial)
    }

    private fun openRealtimeConnection() {
        realtimeConversationRepository.listenMessages(
            object : RealtimeChat.MessageListener {
                override fun onMessageReceived(message: Message) {
                    _uiState.update {
                        it.copy(
                            messages = it.messages + message,
                            realtimeDetail = ConversationState.RealtimeDetail.NewMessage
                        )
                    }

                    /**
                     * ketika terdapat pesan baru dari other user dan user saat ini
                     * sedang membuka conversation room, maka update read checkpoint
                     * ke pesan paling akhir
                     */
                    updateReadCheckpoint()
                }
            }
        )

        realtimeConversationRepository.listenTypingStatus(
            object : RealtimeChat.TypingStatusListener {
                override fun onTypingStatusReceived(isTyping: Boolean) = _uiState.update {
                    it.copy(isOtherUserTyping = isTyping)
                }
            }
        )

        realtimeConversationRepository.listenReadCheckpoint(
            object : RealtimeChat.ReadCheckpointListener {
                override fun onReadCheckpointReceived(readCheckpoint: ReadCheckpoint) {
                    if (readCheckpoint.userId == parameters.otherUserId)
                        _uiState.update {
                            it.copy(
                                otherUser = it.otherUser.copy(
                                    lastReadMessageId = readCheckpoint.lastReadMessageId
                                )
                            )
                        }
                }
            }
        )

        realtimeConversationRepository.openConnection(
            roomId = parameters.roomId,
            currentUserId = session.id,
            otherUserId = parameters.otherUserId
        )
    }

    @SuppressLint("NewApi")
    fun sendMessage(message: String) = viewModelScope.launch {
        val uuid = UUID.randomUUID().toString()

        val currentTime = LocalDateTime.now(ZoneOffset.UTC).toString()
        val newMessage = Message(
            id = uuid,
            message = message,
            roomId = parameters.roomId,
            userId = session.id,
            isDeleted = false,
            createdAt = currentTime,
            updatedAt = currentTime
        )

        /**
         * logika untuk mendapatkan jumlah pesan yang belum dibaca
         * oleh other user, yaitu dengan cara mencari index pesan
         * yang terakhir dibaca, kemudian total pesan dikurangi
         * dengan index tersebut
         */
        var otherUnreadMessageCount = 1
        with(uiState.value) {
            val lastReadMessageId = otherUser.lastReadMessageId
            val lastReadMessageIndex = messages.indexOfLast { it.id == lastReadMessageId }
            if (lastReadMessageIndex != -1) {
                otherUnreadMessageCount = messages.subList(lastReadMessageIndex, messages.size)
                    .count { it.userId == session.id }
            }
        }

        _uiState.update {
            it.copy(
                sendingMessageUUIDs = it.sendingMessageUUIDs + uuid,
                messages = it.messages + newMessage,
                realtimeDetail = ConversationState.RealtimeDetail.NewMessage
            )
        }
        chatRepository.sendMessage(
            roomId = parameters.roomId,
            id = uuid,
            message = message,
            otherId = parameters.otherUserId,
            otherUnreadMessageCount = otherUnreadMessageCount
        ).fold(
            ifLeft = {
                _uiState.update {
                    it.copy(failedMessageUUIDs = it.failedMessageUUIDs + uuid)
                }
            },
            ifRight = { right ->
                realtimeConversationRepository.notifyMessage(
                    message = newMessage.copy(
                        createdAt = right.createdAt,
                        updatedAt = right.updatedAt
                    )
                )
                realtimeRoomRepository.notify(
                    otherUserId = parameters.otherUserId,
                    room = Room(
                        id = parameters.roomId,
                        lastMessage = newMessage.message,
                        unreadMessageCount = otherUnreadMessageCount,
                        otherUser = OtherUser(
                            id = session.id,
                            name = session.name
                        ),
                        lastSentUserId = session.id,
                        createdAt = right.createdAt,
                        updatedAt = right.updatedAt
                    )
                )

                // todo: notify current user

                _uiState.update {
                    it.copy(
                        sendingMessageUUIDs = it.sendingMessageUUIDs - uuid,
                    )
                }
            }
        )
    }

    fun getAllMessages() = viewModelScope.launch {
        _uiState.update { it.copy(detail = ConversationState.Detail.Loading) }
        chatRepository.getAllMessages(roomId = parameters.roomId).fold(
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
                        otherUser = right.otherUser,
                        messages = right.messages,
                        detail = ConversationState.Detail.Success
                    )
                }

                /**
                 * ketika semua history chat berhasil dimuat, maka update checkpoint
                 * ke pesan paling akhir
                 */
                updateReadCheckpoint()
            }
        )
    }

    fun notifyTypingStatus(isTyping: Boolean) {
        _uiState.update { it.copy(isCurrentUserTyping = isTyping) }
        realtimeConversationRepository.notifyTypingStatus(
            roomId = when (isTyping) {
                true -> parameters.roomId
                false -> ""
            }
        )
    }

    /**
     * fungsi updateReadCheckpoint digunakan untuk mengupdate checkpoint chat
     * yang terakhir dibaca oleh user yang sedang login saat ini.
     * fungsi ini dipanggil berdasarkan beberapa kondisi berikut:
     * - ketika history pesan berhasil dimuat dari database
     * - ketika user sedang berada di chat room dan terdapat chat baru
     */
    private fun updateReadCheckpoint() = viewModelScope.launch {
        val lastMessage = uiState.value.messages.lastOrNull { it.userId != session.id }
        if (lastMessage != null)
            chatRepository.updateReadCheckpoint(
                roomId = parameters.roomId,
                lastReadMessageId = lastMessage.id
            ).onRight {
                /**
                 * kirim notifikasi ke broker mqtt sehingga other user
                 * mengetahui perubahan status baca tersebut
                 */
                realtimeConversationRepository.notifyReadCheckpoint(
                    readCheckpoint = ReadCheckpoint(
                        userId = session.id,
                        lastReadMessageId = lastMessage.id
                    )
                )

                // todo: notify current user
            }
    }

    private fun notifyRoomToCurrentUser() {
        val currentUserId = uiState.value.session?.id ?: ""
    }
}