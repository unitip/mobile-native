package com.unitip.mobile.features.chat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.chat.data.repositories.RealtimeRoomRepository
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeRoom
import com.unitip.mobile.features.chat.domain.models.Room
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
    private val realtimeRoomRepository: RealtimeRoomRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    companion object {
        private const val TAG = "ChatsViewModel"
    }

    private val _uiState = MutableStateFlow(ChatsState())
    val uiState get() = _uiState.asStateFlow()

    init {
        // read session
        _uiState.update { it.copy(session = sessionManager.read()) }

        getAllRooms()
    }

    fun resetRealtimeState() =
        _uiState.update {
            it.copy(
                realtimeDetail = ChatsState.RealtimeDetail.Initial
            )
        }

    fun openConnection() {
        val currentUserId = uiState.value.session?.id
        if (currentUserId != null) {
            realtimeRoomRepository.listen(
                object : RealtimeRoom.Listener {
                    override fun onRoomReceived(room: Room) {
                        _uiState.update {
                            val currentRooms = it.rooms
                            val index = currentRooms.indexOfFirst { currentRoom ->
                                currentRoom.id == room.id
                            }

                            when (index == -1) {
                                true -> it.copy(
                                    rooms = (currentRooms + room)
                                        .sortedBy { item -> item.updatedAt }
                                )

                                else -> it.copy(
                                    rooms = currentRooms.map { item ->
                                        when (item.id == room.id) {
                                            true -> room
                                            else -> item
                                        }
                                    }.sortedBy { item -> item.updatedAt }
                                )
                            }
                        }
                    }
                }
            )

            realtimeRoomRepository.openConnection(
                currentUserId = currentUserId
            )
        }
    }

    fun closeConnection() = realtimeRoomRepository.unsubscribeFromTopics()

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