package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {
    private val _uiState = MutableStateFlow(OfferState())
    val uiState get() = _uiState

    private var currentType: String? = null

    init {
        // memuat authenticated user session untuk validasi role
        _uiState.update {
            it.copy(session = sessionManager.read())
        }
        // ini untuk refresh ngambil Offer baru
        fetchOffers()
    }

    fun setTypeFilter(type: String?) {
        currentType = type
        fetchOffers()
    }


    fun expandOffer(offerId : String) = _uiState.update {
        it.copy(expandOfferId =if(it.expandOfferId == offerId) "" else offerId)
    }

    fun refreshOffer() = fetchOffers()

    private fun fetchOffers() = viewModelScope.launch {
        _uiState.update { it.copy(detail = OfferState.Detail.Loading) }
        offerRepository.getOffers(type = currentType).fold(
            ifLeft = { left ->
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

    fun loadMore() = viewModelScope.launch {
        if (_uiState.value.detail !is OfferState.Detail.Loading) {
            val currentPage = _uiState.value.currentPage
            val nextPage = currentPage + 1

            offerRepository.getOffers(page = nextPage, type = currentType).fold(
                ifLeft = { left ->
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
                            result = it.result.copy(
                                offers = it.result.offers + right.offers,
                                hasNext = right.hasNext
                            ),
                            currentPage = nextPage
                        )
                    }
                }
            )
        }
    }
}