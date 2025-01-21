package com.unitip.mobile.features.offer.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.domain.models.GetAllJobsResult
import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import com.unitip.mobile.features.offer.data.models.CreateOfferResult
import com.unitip.mobile.features.offer.data.sources.OfferApi
import com.unitip.mobile.features.offer.domain.models.GetAllOffersResult
import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.features.offer.domain.models.OfferFreelancer
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val offerApi:OfferApi
){
    suspend fun create(
        title: String,
        description: String,
        price: Number,
        type: String,
        pickupArea: String,
        deliveryArea: String,
        availableUntil: String
    ):Either<Failure,CreateOfferResult>{
        try {
            val token = sessionManager.read()?.token
            val response = offerApi.create(
                token="Bearer $token",
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
           return when (response.isSuccessful && result != null){
               true -> Either.Right(CreateOfferResult(id = result.id))
               false -> Either.Left(response.mapToFailure())
           }
        }catch (e: Exception){
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }


    suspend fun getAll() : Either<Failure,GetAllOffersResult>{
    try {
        val token = sessionManager.read()?.token
        val response = offerApi.getAll(token="Bearer $token", type = GetAllOfferResponse.PageInfo.OfferType.SINGLE.value)
        val result = response.body()

        return when (response.isSuccessful && result != null){
            true -> Either.Right(
                GetAllOffersResult(
                    offers = result.offers.map {
                        Offer(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            price = it.price,
                            type = it.type,
                            pickupArea = it.pickupArea,
                            deliveryArea = it.deliveryArea,
                            availableUntil = it.availableUntil,
                            offerStatus = it.offerStatus,
                            freelancer = OfferFreelancer(
                                name = it.freelancer.name
                            )
                        )
                    },
                    hasNext = result.pageInfo.page < result.pageInfo.totalPages
                )
            )

            false -> Either.Left(response.mapToFailure())
        }

        } catch (e: Exception){
            e.printStackTrace()
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga !"))
        }
    }
}