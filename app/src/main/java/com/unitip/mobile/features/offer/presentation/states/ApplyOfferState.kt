package com.unitip.mobile.features.offer.presentation.states

import org.osmdroid.util.GeoPoint

data class ApplyOfferState(
    val note: String = "",
    val finalPrice: Int = 0,
    val pickupLocation: String = "",
    val destinationLocation: String = "",
    val pickupLatitude: Double = 0.0,
    val pickupLongitude: Double = 0.0,
    val destinationLatitude: Double = 0.0,
    val destinationLongitude: Double = 0.0,
    val pickupLocationGeoPoint: GeoPoint? = null,
    val destinationLocationGeoPoint: GeoPoint? = null,
    val detail: Detail = Detail.Initial
) {
    sealed class Detail {
        data object Initial : Detail()
        data object Loading : Detail()
        data object Success : Detail()
        data class Error(val message: String) : Detail()
    }
}