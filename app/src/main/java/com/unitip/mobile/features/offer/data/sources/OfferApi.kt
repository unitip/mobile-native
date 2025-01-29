package com.unitip.mobile.features.offer.data.sources

import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.CreateOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetOfferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OfferApi {
    @POST("offers")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateOfferPayload
    ): Response<CreateOfferResponse>

    @GET("offers")
    suspend fun getOffers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String? = null // Optional type filter
    ): Response<GetOfferResponse>

//    /api/v1/offers/{offer_id}/apply/multi
//    /api/v1/offers/{offer_id}/apply/single
}