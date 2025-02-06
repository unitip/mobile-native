package com.unitip.mobile.features.offer.domain.models

data class DetailApplicantOffer(
    val id: String = "",
    val customer: Customer = Customer(),
    val pickupLocation: String = "",
    val destinationLocation: String = "",
    val pickupCoordinates: Coordinates = Coordinates(),
    val destinationCoordinates: Coordinates = Coordinates(),
    val note: String = "",
    val applicantStatus: String = "",
    val finalPrice: Int = 0
) {
    data class Customer(
        val id: String = "",
        val name: String = ""
    )

    data class Coordinates(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    )
}
