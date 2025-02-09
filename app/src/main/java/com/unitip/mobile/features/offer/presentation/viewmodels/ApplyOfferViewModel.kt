package com.unitip.mobile.features.offer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.unitip.mobile.features.offer.data.repositories.OfferRepository
import com.unitip.mobile.features.offer.presentation.states.ApplyOfferState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class ApplyOfferViewModel @Inject constructor(
    private val offerRepository: OfferRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ApplyOfferState())
    val state = _state.asStateFlow()

    fun onNoteChange(note: String) {
        _state.update { it.copy(note = note) }
    }

    fun onFinalPriceChange(finalPrice: Int) {
        _state.update { it.copy(finalPrice = finalPrice) }
    }

    fun onPickupLocationChange(location: String) {
        _state.update {
            it.copy(
                pickupLocation = location
            )
        }
    }

    fun onDestinationLocationChange(location: String) {
        _state.update {
            it.copy(
                destinationLocation = location
            )
        }
    }

    fun onPickupLocationGeoPointChange(geoPoint: GeoPoint) {
        _state.update {
            it.copy(
                pickupLocationGeoPoint = geoPoint,
                pickupLatitude = geoPoint.latitude,
                pickupLongitude = geoPoint.longitude
            )
        }
    }

    fun onDestinationLocationGeoPointChange(geoPoint: GeoPoint) {
        _state.update {
            it.copy(
                destinationLocationGeoPoint = geoPoint,
                destinationLatitude = geoPoint.latitude,
                destinationLongitude = geoPoint.longitude
            )
        }
    }

    fun applyOffer(offerId: String) {
        viewModelScope.launch {

            _state.update { it.copy(detail = ApplyOfferState.Detail.Loading) }

            val result = offerRepository.applyOffer(
                offerId = offerId,
                note = state.value.note,
                finalPrice = state.value.finalPrice,
                pickupLocation = state.value.pickupLocation,
                destinationLocation = state.value.destinationLocation,
                pickupLatitude = state.value.pickupLatitude,
                pickupLongitude = state.value.pickupLongitude,
                destinationLatitude = state.value.destinationLatitude,
                destinationLongitude = state.value.destinationLongitude
            )

            when (result) {
                is Either.Left -> {
                    _state.update {
                        it.copy(detail = ApplyOfferState.Detail.Error(result.value.message))
                    }
                }

                is Either.Right -> {
                    _state.update {
                        it.copy(detail = ApplyOfferState.Detail.Success)
                    }
                }
            }
        }
    }
}
