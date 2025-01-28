package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.left
import arrow.core.right
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.presentation.states.OfferState
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OffersViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    val sessionManager: SessionManager
): ViewModel(){
    private val _uiState = MutableStateFlow(OfferState())
    val uiState get() = _uiState

    init {
        // memuat authenticated user session untuk validasi role
        _uiState.update {
            it.copy(session = sessionManager.read())
        }
        // ini untuk refresh ngambil Offer baru
        fetchOffer()
    }

    fun expandOffer(offerId : String) = _uiState.update {
        it.copy(expandOfferId =if(it.expandOfferId == offerId) "" else offerId)
    }

    fun refreshOffer() = fetchOffer()

    private fun fetchOffer() = viewModelScope.launch {
        _uiState.update { it.copy(detail = OfferState.Detail.Loading) }
        offerRepository.getAllOffers().fold(
            ifLeft = { left->
                _uiState.update {
                    it.copy(
                        detail = OfferState.Detail.Failure(message = left.message)
                    )
                }
            },
            ifRight = { right ->
                _uiState.update {
                    it.copy(
                        detail = OfferState.Detail.Success,
                        result = right
                    )
                }
            }
        )
    }
}