package com.unitip.mobile.features.offer.data.sources

import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.CreateOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetMultiOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetSingleOfferResponse
import kotlinx.serialization.descriptors.SerialKind
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OfferApi {
    //post offer yang masuk ke tabel single_offer
    @POST("offers")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateOfferPayload
    ): Response<CreateOfferResponse>

    //get all tabel single_offer
    @GET("offers")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String

    ):Response<GetAllOfferResponse>

    // Get All Offers (all types)
    @GET("offers2")
    suspend fun getAllOffers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<GetAllOfferResponse>

    // Get Multi Offers
    @GET("offers2/multi")
    suspend fun getMultiOffers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<GetMultiOfferResponse>

    // Get Single Offers
    @GET("offers2/single")
    suspend fun getSingleOffers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<GetSingleOfferResponse>
//    /api/v1/offers/{offer_id}/apply/multi
//    /api/v1/offers/{offer_id}/apply/single
}