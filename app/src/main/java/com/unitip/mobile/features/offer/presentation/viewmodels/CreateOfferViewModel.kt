package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.presentation.states.CreateOfferState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOfferViewModel @Inject constructor(
    private val offerRepository: OfferRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CreateOfferState())
    val uiState get() = _uiState

    fun resetState(){
        _uiState.value = with(uiState.value){
            copy(detail = CreateOfferState.Detail.Initial)
        }
    }

    fun create(
        title: String,
        description: String,
        type: String,
        availableUntil: String,
        price: Number,
        pickupArea: String,
        destinationArea: String,
        maxParticipants: Number
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = CreateOfferState.Detail.Loading) }
        offerRepository.create(
            title = title,
            description= description,
            type = type,
            availableUntil = availableUntil,
            price = price,
            pickupArea = pickupArea,
            destinationArea = destinationArea,
            maxParticipants = maxParticipants
        ).fold(
            ifLeft = {
                left -> _uiState.update {
                    it.copy(
                        detail = CreateOfferState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(
                        detail = CreateOfferState.Detail.Success
                    )
                }
            }
        )
    }
}