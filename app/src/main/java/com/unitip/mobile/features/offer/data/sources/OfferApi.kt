package com.unitip.mobile.features.offer.data.sources

import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.CreateOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OfferApi {
    //post offer yang masuk ke tabel single_offer
    @POST("offer")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateOfferPayload
    ): Response<CreateOfferResponse>

    //get all tabel single_offer
    @GET("offer")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
    ):Response<GetAllOfferResponse>
}