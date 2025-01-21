package com.unitip.mobile.features.chat.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.data.repositories.RealtimeChatRepository
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeChat
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.domain.models.ReadCheckpoint
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
    private val chatRepository: ChatRepository,
    private val sessionManager: SessionManager,
    private val realtimeChatRepository: RealtimeChatRepository
) : ViewModel() {
    companion object {
        private const val TAG = "ConversationViewModel"
    }

    private val _uiState = MutableStateFlow(ConversationState())
    val uiState get() = _uiState.asStateFlow()

    init {
        // read session dari session manager
        _uiState.update { it.copy(session = sessionManager.read()) }
    }

    fun resetRealtimeState() = _uiState.update {
        it.copy(realtimeDetail = ConversationState.RealtimeDetail.Initial)
    }

    fun openRealtimeConnection(
        roomId: String,
        otherUserId: String
    ) {
        val currentUserId = uiState.value.session?.id ?: ""
        if (currentUserId.isNotEmpty() && otherUserId.isNotEmpty()) {
            realtimeChatRepository.listenMessages(
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
                        updateReadCheckpoint(roomId = roomId)
                    }
                }
            )

            realtimeChatRepository.listenTypingStatus(
                object : RealtimeChat.TypingStatusListener {
                    override fun onTypingStatusReceived(isTyping: Boolean) = _uiState.update {
                        it.copy(isOtherUserTyping = isTyping)
                    }
                }
            )

            realtimeChatRepository.listenReadCheckpoint(
                object : RealtimeChat.ReadCheckpointListener {
                    override fun onReadCheckpointReceived(readCheckpoint: ReadCheckpoint) {
                        if (readCheckpoint.userId == otherUserId)
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

            realtimeChatRepository.openConnection(
                roomId = roomId,
                currentUserId = currentUserId,
                otherUserId = otherUserId
            )
        }
    }

    fun closeRealtimeConnection() = realtimeChatRepository.unsubscribeFromTopics()

    @SuppressLint("NewApi")
    fun sendMessage(
        roomId: String,
        message: String,
        otherUserId: String
    ) = viewModelScope.launch {
        val uuid = UUID.randomUUID().toString()
        val userId = uiState.value.session?.id ?: ""

        val currentTime = LocalDateTime.now(ZoneOffset.UTC).toString()
        val newMessage = Message(
            id = uuid,
            message = message,
            roomId = roomId,
            userId = userId,
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
            if (lastReadMessageIndex != -1)
                otherUnreadMessageCount = messages.size - lastReadMessageIndex
        }

        _uiState.update {
            it.copy(
                sendingMessageUUIDs = it.sendingMessageUUIDs + uuid,
                messages = it.messages + newMessage,
                realtimeDetail = ConversationState.RealtimeDetail.NewMessage
            )
        }
        chatRepository.sendMessage(
            roomId = roomId,
            id = uuid,
            message = message,
            otherId = otherUserId,
            otherUnreadMessageCount = otherUnreadMessageCount
        ).fold(
            ifLeft = {
                _uiState.update {
                    it.copy(failedMessageUUIDs = it.failedMessageUUIDs + uuid)
                }
            },
            ifRight = { right ->
                realtimeChatRepository.notifyMessage(
                    message = newMessage.copy(
                        createdAt = right.createdAt,
                        updatedAt = right.updatedAt
                    )
                )
                _uiState.update {
                    it.copy(
                        sendingMessageUUIDs = it.sendingMessageUUIDs - uuid,
                    )
                }
            }
        )
    }

    fun getAllMessages(roomId: String) = viewModelScope.launch {
        _uiState.update { it.copy(detail = ConversationState.Detail.Loading) }
        chatRepository.getAllMessages(roomId = roomId).fold(
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
                updateReadCheckpoint(roomId = roomId)
            }
        )
    }

    fun notifyTypingStatus(
        roomId: String,
        isTyping: Boolean
    ) {
        _uiState.update { it.copy(isTyping = isTyping) }
        realtimeChatRepository.notifyTypingStatus(
            roomId = when (isTyping) {
                true -> roomId
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
    private fun updateReadCheckpoint(roomId: String) = viewModelScope.launch {
        val currentUserId = uiState.value.session?.id ?: ""
        val lastMessage = uiState.value.messages.lastOrNull { it.userId != currentUserId }
        if (lastMessage != null)
            chatRepository.updateReadCheckpoint(
                roomId = roomId,
                lastReadMessageId = lastMessage.id
            ).onRight {
                /**
                 * kirim notifikasi ke broker mqtt sehingga other user
                 * mengetahui perubahan status baca tersebut
                 */
                realtimeChatRepository.notifyReadCheckpoint(
                    readCheckpoint = ReadCheckpoint(
                        userId = currentUserId,
                        lastReadMessageId = lastMessage.id
                    )
                )
            }
    }
}