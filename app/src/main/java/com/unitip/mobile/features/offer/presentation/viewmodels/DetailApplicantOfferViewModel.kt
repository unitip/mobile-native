package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.presentation.states.DetailApplicantOfferState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailApplicantOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    private val offerRepository: OfferRepository
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
        // Hanya set loading state untuk updateStatus
        _uiState.update {
            it.copy(
                updateStatus = DetailApplicantOfferState.UpdateStatus.Loading
            )
        }

        offerRepository.updateApplicantStatus(offerId, applicantId, newStatus).fold(
            ifLeft = { failure ->
                _uiState.update {
                    it.copy(
                        updateStatus = DetailApplicantOfferState.UpdateStatus.Failure(failure.message)
                    )
                }
            },
            ifRight = { response ->
                if (response.status) {
                    // Langsung fetch data baru tanpa mengubah state detail menjadi loading
                    offerRepository.getApplicantDetail(offerId, applicantId).fold(
                        ifLeft = { failure ->
                            _uiState.update {
                                it.copy(
                                    detail = DetailApplicantOfferState.Detail.Failure(failure.message),
                                    updateStatus = DetailApplicantOfferState.UpdateStatus.Initial
                                )
                            }
                        },
                        ifRight = { applicant ->
                            _uiState.update {
                                it.copy(
                                    detail = DetailApplicantOfferState.Detail.Success,
                                    applicant = applicant,
                                    updateStatus = DetailApplicantOfferState.UpdateStatus.Initial
                                )
                            }
                        }
                    )
                } else {
                    _uiState.update {
                        it.copy(
                            updateStatus = DetailApplicantOfferState.UpdateStatus.Failure(response.message)
                        )
                    }
                }
            }
        )
    }
}

