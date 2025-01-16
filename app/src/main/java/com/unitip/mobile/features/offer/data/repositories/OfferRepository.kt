package com.unitip.mobile.features.offer.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.domain.models.GetAllJobsResult
import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import com.unitip.mobile.features.offer.data.models.CreateOfferResult
import com.unitip.mobile.features.offer.data.sources.OfferApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val offerAPI:OfferApi
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
            val response = offerAPI.create(
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

    // ini harus bikin dari model dulu, jadi nanti aja dulu
//    suspend fun getAll() : Either<Failure,GetAllOfferResponse>{
//
//    }
}