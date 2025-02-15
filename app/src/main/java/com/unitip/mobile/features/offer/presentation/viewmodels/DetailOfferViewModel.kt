package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.offer.commons.OfferRoutes
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.features.offer.presentation.states.DetailOfferState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    private val offerRepository: OfferRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {
    companion object {
        private const val TAG = "DetailOfferViewModel"
    }

    private val session = sessionManager.read()
    private val parameters = savedStateHandle.toRoute<OfferRoutes.DetailOfferCustomer>()

    private val _uiState = MutableStateFlow(DetailOfferState())
    val uiState get() = _uiState.asStateFlow()

    private val _offer = MutableStateFlow(Offer())
    val offer get() = _offer.asStateFlow()

    init {
        _uiState.update { it.copy(session = session) }
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {

        _uiState.update { it.copy(detail = DetailOfferState.Detail.Loading) }
        offerRepository.getOfferDetail(parameters.offerId).fold(
            ifLeft = { failure ->
                _uiState.update {
                    it.copy(
                        detail = DetailOfferState.Detail.Failure(message = failure.message)
                    )
                }
            },
            ifRight = { offer ->
                _offer.value = offer
                _uiState.update {
                    it.copy(
                        detail = DetailOfferState.Detail.Success
                    )
                }
            }
        )
    }

    fun createChat(driverId: String) = viewModelScope.launch {
        val members = listOf(session.id, driverId)

        // Cek room yang sudah ada
        chatRepository.checkRoom(members).fold(
            ifLeft = { failure ->
                _uiState.update {
                    it.copy(error = failure.message)
                }
            },
            ifRight = { existingRoomId ->
                if (existingRoomId != null) {
                    // Gunakan room yang sudah ada
                    _uiState.update {
                        it.copy(
                            navigateToChat = Triple(
                                existingRoomId,
                                driverId,
                                offer.value.freelancer.name
                            )
                        )
                    }
                } else {
                    // Buat room baru jika belum ada
                    chatRepository.createRoom(members).fold(
                        ifLeft = { failure ->
                            _uiState.update {
                                it.copy(error = failure.message)
                            }
                        },
                        ifRight = { roomId ->
                            _uiState.update {
                                it.copy(
                                    navigateToChat = Triple(
                                        roomId,
                                        driverId,
                                        offer.value.freelancer.name
                                    )
                                )
                            }
                        }
                    )
                }
            }
        )
    }
    fun resetNavigateToChat() {
        _uiState.update { it.copy(navigateToChat = null) }
    }
}