package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.shared.commons.constants.ServiceTypeConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.offer.presentation.states.CreateOfferState as State

@HiltViewModel
class CreateOfferViewModel @Inject constructor(
    private val offerRepository: OfferRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState

    fun resetState() = _uiState.update {
        it.copy(detail = State.Detail.Initial)
    }

    fun create(
        title: String,
        description: String,
        type: ServiceTypeConstant,
        availableUntil: String,
        price: Int,
        pickupArea: String,
        destinationArea: String,
        maxParticipants: Int
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        offerRepository
            .create(
                title = title,
                description = description,
                type = type,
                availableUntil = availableUntil,
                price = price,
                pickupArea = pickupArea,
                destinationArea = destinationArea,
                maxParticipants = maxParticipants
            )
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Success
                    )
                }
            }
    }
}