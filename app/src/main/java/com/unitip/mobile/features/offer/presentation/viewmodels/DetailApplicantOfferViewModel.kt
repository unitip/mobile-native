package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.chat.data.repositories.ChatRepository
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.presentation.states.DetailApplicantOfferState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailApplicantOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    private val offerRepository: OfferRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {
    private val session = sessionManager.read()
    private val offerId = savedStateHandle.get<String>("offerId") ?: ""
    private val applicantId = savedStateHandle.get<String>("applicantId") ?: ""

    private val _uiState = MutableStateFlow(DetailApplicantOfferState(session = session))
    val uiState = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.update { it.copy(detail = DetailApplicantOfferState.Detail.Loading) }
        offerRepository.getApplicantDetail(offerId, applicantId).fold(
            ifLeft = { failure ->
                _uiState.update {
                    it.copy(detail = DetailApplicantOfferState.Detail.Failure(failure.message))
                }
            },
            ifRight = { applicant ->
                _uiState.update {
                    it.copy(
                        detail = DetailApplicantOfferState.Detail.Success,
                        applicant = applicant
                    )
                }
            }
        )
    }

    fun updateStatus(newStatus: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Loading)
        }

        offerRepository.updateApplicantStatus(offerId, applicantId, newStatus).fold(
            ifLeft = { failure ->
                _uiState.update {
                    it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Failure(failure.message))
                }
                delay(500)
                _uiState.update {
                    it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Initial)
                }
                fetchData() // Tetap refresh data meskipun gagal
            },
            ifRight = { response ->
                if (response.status) {
                    fetchData()
                    _uiState.update {
                        it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Success)
                    }
                    // Reset status setelah beberapa detik
                    delay(500)
                    _uiState.update {
                        it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Initial)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            updateStatus = DetailApplicantOfferState.UpdateStatus.Failure(
                                response.message
                            )
                        )
                    }
                    delay(500)
                    _uiState.update {
                        it.copy(updateStatus = DetailApplicantOfferState.UpdateStatus.Initial)
                    }
                    fetchData()
                }
            }
        )
    }

    fun createChat(customerId: String) = viewModelScope.launch {
        val members = listOf(session.id, customerId)

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
                                customerId,
                                uiState.value.applicant.customer.name
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
                                        customerId,
                                        uiState.value.applicant.customer.name
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

