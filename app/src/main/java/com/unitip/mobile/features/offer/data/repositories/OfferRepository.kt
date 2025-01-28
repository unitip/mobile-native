package com.unitip.mobile.features.offer.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetMultiOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetSingleOfferResponse
import com.unitip.mobile.features.offer.data.models.CreateOfferResult
import com.unitip.mobile.features.offer.data.sources.OfferApi
import com.unitip.mobile.features.offer.domain.models.GetAllOffersResult
import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.features.offer.domain.models.OfferFreelancer
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import com.unitip.mobile.shared.domain.models.Session
import retrofit2.Response
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
        deliveryArea: String,
        availableUntil: String
    ): Either<Failure, CreateOfferResult> {
        try {
            val token = sessionManager.read()?.token
            val response = offerApi.create(
                token = "Bearer $token",
                payload = CreateOfferPayload(
                    title = title,
                    description = description,
                    price = price,
                    type = type,
                    pickupArea = pickupArea,
                    deliveryArea = deliveryArea,
                    availableUntil = availableUntil,
                )
            )

            val result = response.body()
            // mereturn jika sukses dan jika tidak
            return when (response.isSuccessful && result != null) {
                true -> Either.Right(CreateOfferResult(id = result.id))
                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun getAllOffers(page: Int = 1): Either<Failure, GetAllOffersResult> {
        return fetchOffers(
            apiCall = { offerApi.getAllOffers("Bearer ${it.token}", page) },
            mapper = { response ->
                response.offers.map { apiOffer ->
                    Offer(
                        id = apiOffer.id,
                        title = apiOffer.title,
                        description = apiOffer.description,
                        price = apiOffer.price,
                        type = apiOffer.type,
                        deliveryArea = apiOffer.deliveryArea,
                        pickupArea = apiOffer.pickupArea,
                        availableUntil = apiOffer.availableUntil,
                        offerStatus = apiOffer.offerStatus,
                        freelancer = OfferFreelancer(name = apiOffer.freelancer.name)
                    )
                }
            }
        )
    }

    suspend fun getMultiOffers(page: Int = 1): Either<Failure, GetAllOffersResult> {
        return fetchOffers(
            apiCall = { offerApi.getMultiOffers("Bearer ${it.token}", page) },
            mapper = { response ->
                response.offers.map { apiOffer ->
                    Offer(
                        id = apiOffer.id,
                        title = apiOffer.title,
                        description = apiOffer.description,
                        price = apiOffer.price,
                        type = apiOffer.type,
                        deliveryArea = apiOffer.deliveryArea,
                        pickupArea = apiOffer.pickupArea,
                        availableUntil = apiOffer.availableUntil,
                        offerStatus = apiOffer.offerStatus,
                        freelancer = OfferFreelancer(name = apiOffer.freelancerName)
                    )
                }
            }
        )
    }

    suspend fun getSingleOffers(page: Int = 1): Either<Failure, GetAllOffersResult> {
        return fetchOffers(
            apiCall = { offerApi.getSingleOffers("Bearer ${it.token}", page) },
            mapper = { response ->
                response.offers.map { apiOffer ->
                    Offer(
                        id = apiOffer.id,
                        title = apiOffer.title,
                        description = apiOffer.description,
                        price = apiOffer.price,
                        type = apiOffer.type,
                        pickupArea = apiOffer.pickupArea,
                        deliveryArea = apiOffer.deliveryArea,
                        availableUntil = apiOffer.availableUntil,
                        offerStatus = apiOffer.offerStatus,
                        freelancer = OfferFreelancer(name = apiOffer.freelancerName)
                    )
                }
            }
        )
    }

    private suspend fun <T> fetchOffers(
        apiCall: suspend (Session) -> Response<T>,
        mapper: (T) -> List<Offer>
    ): Either<Failure, GetAllOffersResult> {
        return try {
            val session = sessionManager.read() ?: return Either.Left(Failure("Unauthorized"))
            val response = apiCall(session)

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Either.Right(
                            GetAllOffersResult(
                                offers = mapper(body),
                                hasNext = getHasNextPage(body)
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

    private fun getHasNextPage(body: Any): Boolean {
        return when (body) {
            is GetAllOfferResponse -> body.pageInfo.page < body.pageInfo.totalPages
            is GetMultiOfferResponse -> body.pageInfo.page < body.pageInfo.totalPages
            is GetSingleOfferResponse -> body.pageInfo.page < body.pageInfo.totalPages
            else -> false
        }
    }
}

//
//    suspend fun getAll(): Either<Failure, GetAllOffersResult> {
//        try {
//            val token = sessionManager.read()?.token
//            val response = offerApi.getAll(
//                token = "Bearer $token",
//                type = GetAllOfferResponse.PageInfo.OfferType.SINGLE.value
//            )
//            val result = response.body()
//
//            return when (response.isSuccessful && result != null) {
//                true -> Either.Right(
//                    GetAllOffersResult(
//                        offers = result.offers.map {
//                            Offer(
//                                id = it.id,
//                                title = it.title,
//                                description = it.description,
//                                price = it.price,
//                                type = it.type,
//                                pickupArea = it.pickupArea,
//                                deliveryArea = it.deliveryArea,
//                                availableUntil = it.availableUntil,
//                                offerStatus = it.offerStatus,
//                                freelancer = OfferFreelancer(
//                                    name = it.freelancer.name
//                                )
//                            )
//                        },
//                        hasNext = result.pageInfo.page < result.pageInfo.totalPages
//                    )
//                )
//
//                false -> Either.Left(response.mapToFailure())
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga !"))
//        }
//    }