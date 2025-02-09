package com.unitip.mobile.features.offer.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.offer.data.dtos.ApplyOfferPayload
import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.GetOfferResponse
import com.unitip.mobile.features.offer.data.dtos.DetailApplicantOfferResponse
import com.unitip.mobile.features.offer.data.dtos.UpdateApplicantStatusPayload
import com.unitip.mobile.features.offer.data.dtos.UpdateApplicantStatusResponse
import com.unitip.mobile.features.offer.data.models.ApplyOfferResult
import com.unitip.mobile.features.offer.data.sources.OfferApi
import com.unitip.mobile.features.offer.domain.models.Applicant
import com.unitip.mobile.features.offer.domain.models.DetailApplicantOffer
import com.unitip.mobile.features.offer.domain.models.GetAllOffersResult
import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.features.offer.domain.models.OfferFreelancer
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val offerApi: OfferApi
) {
    suspend fun create(
        title: String,
        description: String,
        price: Number,
        type: String,
        pickupArea: String,
        destinationArea: String,
        availableUntil: String,
        maxParticipants: Number
    ): Either<Failure, Unit> {
        try {
            val token = sessionManager.read().token
            val response = offerApi.create(
                token = "Bearer $token",
                payload = CreateOfferPayload(
                    title = title,
                    description = description,
                    price = price,
                    type = type,
                    pickupArea = pickupArea,
                    destinationArea = destinationArea,
                    availableUntil = availableUntil,
                    maxParticipants = maxParticipants
                )
            )

            val result = response.body()
            // mereturn jika sukses dan jika tidak
            return when (response.isSuccessful && result != null) {
                true -> Either.Right(Unit)
                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun getOffers(
        page: Int = 1,
        type: String? = null
    ): Either<Failure, GetAllOffersResult> {
        return try {
            val session = sessionManager.read()
            val response = offerApi.getAllOffers(
                token = "Bearer ${session.token}",
                page = page,
                type = type
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(
                            GetAllOffersResult(
                                offers = body.offers.map { apiOffer ->
                                    Offer(
                                        id = apiOffer.id,
                                        title = apiOffer.title,
                                        description = apiOffer.description,
                                        price = apiOffer.price,
                                        type = apiOffer.type,
                                        destinationArea = apiOffer.destinationArea,
                                        pickupArea = apiOffer.pickupArea,
                                        availableUntil = apiOffer.availableUntil,
                                        offerStatus = apiOffer.offerStatus,
                                        maxParticipants = apiOffer.maxParticipants,
                                        freelancer = OfferFreelancer(
                                            id = apiOffer.freelancer.id,
                                            name = apiOffer.freelancer.name)
                                    )
                                },
                                hasNext = body.pageInfo.page < body.pageInfo.totalPages
                            )
                        )
                    } else {
                        Either.Left(Failure("Response body is null"))
                    }
                }

                else -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure("Terjadi kesalahan: ${e.message}"))
        }
    }

    suspend fun getOfferDetail(offerId: String): Either<Failure, Offer> {
        return try {
            val session = sessionManager.read()
            val response = offerApi.getOfferDetail(
                token = "Bearer ${session.token}",
                offerId = offerId
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(body.offer.toDomainModel())
                    } else {
                        Either.Left(Failure("Response body is null"))
                    }
                }

                else -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure("Terjadi kesalahan: ${e.message}"))
        }
    }

    private fun GetOfferResponse.ApiOffer.toDomainModel(): Offer {
        return Offer(
            id = this.id,
            title = this.title,
            description = this.description,
            price = this.price,
            type = this.type,
            pickupArea = this.pickupArea,
            destinationArea = this.destinationArea,
            availableUntil = this.availableUntil,
            offerStatus = this.offerStatus,
            freelancer = OfferFreelancer(
                id = this.freelancer.id,
                name = this.freelancer.name
            ),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            applicantsCount = this.applicantsCount,
            hasApplied = this.hasApplied,
            maxParticipants = this.maxParticipants,
            applicants = this.applicants.map { it.toDomainModel() }
        )
    }

    private fun GetOfferResponse.ApiApplicant.toDomainModel(): Applicant {
        return Applicant(
            id = this.id,
            customerId = this.customerId,
            customerName = this.customerName,
            pickupLocation = this.pickupLocation,
            destinationLocation = this.destinationLocation,
            note = this.note,
            status = this.applicantStatus,
            finalPrice = this.finalPrice
        )
    }

    suspend fun applyOffer(
        offerId: String,
        note: String,
        destinationLocation: String,
        pickupLocation: String,
        pickupLatitude: Double,
        pickupLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): Either<Failure, ApplyOfferResult> {
        return try {
            val session = sessionManager.read()
            val response = offerApi.applyOffer(
                token = "Bearer ${session.token}",
                offerId = offerId,
                payload = ApplyOfferPayload(
                    note = note,
                    destinationLocation = destinationLocation,
                    pickupLocation = pickupLocation,
                    pickupLatitude = pickupLatitude,
                    pickupLongitude = pickupLongitude,
                    destinationLatitude = destinationLatitude,
                    destinationLongitude = destinationLongitude
                )
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(ApplyOfferResult(id = body.id))
                    } else {
                        Either.Left(Failure("Response body is null"))
                    }
                }

                else -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure("Terjadi kesalahan: ${e.message}"))
        }
    }

    suspend fun getApplicantDetail(
        offerId: String,
        applicantId: String
    ): Either<Failure, DetailApplicantOffer> {
        return try {
            val session = sessionManager.read()
            val response = offerApi.getApplicantDetail(
                token = "Bearer ${session.token}",
                offerId = offerId,
                applicantId = applicantId
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(body.applicant.toDomainModel())
                    } else {
                        Either.Left(Failure("Response body is null"))
                    }
                }
                else -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure("Terjadi kesalahan: ${e.message}"))
        }
    }

    private fun DetailApplicantOfferResponse.ApiApplicant.toDomainModel(): DetailApplicantOffer {
        return DetailApplicantOffer(
            id = this.id,
            customer = DetailApplicantOffer.Customer(
                id = this.customer.id,
                name = this.customer.name
            ),
            pickupLocation = this.pickupLocation,
            destinationLocation = this.destinationLocation,
            pickupCoordinates = DetailApplicantOffer.Coordinates(
                latitude = this.pickupCoordinates.latitude,
                longitude = this.pickupCoordinates.longitude
            ),
            destinationCoordinates = DetailApplicantOffer.Coordinates(
                latitude = this.destinationCoordinates.latitude,
                longitude = this.destinationCoordinates.longitude
            ),
            note = this.note,
            applicantStatus = this.applicantStatus,
            finalPrice = this.finalPrice
        )
    }

    suspend fun updateApplicantStatus(
        offerId: String,
        applicantId: String,
        status: String
    ): Either<Failure, UpdateApplicantStatusResponse> {
        return try {
            val session = sessionManager.read()
            val response = offerApi.updateApplicantStatus(
                token = "Bearer ${session.token}",
                offerId = offerId,
                applicantId = applicantId,
                payload = UpdateApplicantStatusPayload(status)
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(body)
                    } else {
                        Either.Left(Failure("Response body is null"))
                    }
                }
                else -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure("Terjadi kesalahan: ${e.message}"))
        }
    }

}