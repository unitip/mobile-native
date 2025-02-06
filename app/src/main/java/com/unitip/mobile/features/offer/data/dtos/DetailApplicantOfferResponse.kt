package com.unitip.mobile.features.offer.data.dtos

import com.google.gson.annotations.SerializedName

data class DetailApplicantResponse(
    @SerializedName("applicant") val applicant: ApiApplicant
) {
    data class ApiApplicant(
        @SerializedName("id") val id: String,
        @SerializedName("customer") val customer: ApiCustomer,
        @SerializedName("pickup_location") val pickupLocation: String,
        @SerializedName("destination_location") val destinationLocation: String,
        @SerializedName("pickup_coordinates") val pickupCoordinates: ApiCoordinates,
        @SerializedName("destination_coordinates") val destinationCoordinates: ApiCoordinates,
        @SerializedName("note") val note: String,
        @SerializedName("applicant_status") val applicantStatus: String,
        @SerializedName("final_price") val finalPrice: Int
    )

    data class ApiCustomer(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String
    )

    data class ApiCoordinates(
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double
    )
}