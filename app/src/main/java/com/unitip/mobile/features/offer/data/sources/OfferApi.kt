package com.unitip.mobile.features.offer.data.sources

import com.unitip.mobile.features.offer.data.dtos.ApplyOfferPayload
import com.unitip.mobile.features.offer.data.dtos.ApplyOfferResponse
import com.unitip.mobile.features.offer.data.dtos.CreateOfferPayload
import com.unitip.mobile.features.offer.data.dtos.CreateOfferResponse
import com.unitip.mobile.features.offer.data.dtos.DetailApplicantOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetAllOfferResponse
import com.unitip.mobile.features.offer.data.dtos.GetOfferResponse
import com.unitip.mobile.features.offer.data.dtos.UpdateApplicantStatusPayload
import com.unitip.mobile.features.offer.data.dtos.UpdateApplicantStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OfferApi {
    @POST("offers")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateOfferPayload
    ): Response<CreateOfferResponse>

    @GET("offers")
    suspend fun getAllOffers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String? = null
    ): Response<GetAllOfferResponse>

    @GET("offers/{id}")
    suspend fun getOfferDetail(
        @Header("Authorization") token: String,
        @Path("id") offerId: String
    ): Response<GetOfferResponse>

    //    Apply
    @POST("offers/{id}/apply")
    suspend fun applyOffer(
        @Header("Authorization") token: String,
        @Path("id") offerId: String,
        @Body payload: ApplyOfferPayload
    ): Response<ApplyOfferResponse>

    @GET("offers/{offer_id}/applicants/{applicant_id}")
    suspend fun getApplicantDetail(
        @Header("Authorization") token: String,
        @Path("offer_id") offerId: String,
        @Path("applicant_id") applicantId: String
    ): Response<DetailApplicantOfferResponse>

    //   Applicant Offer status
    @PATCH("offers/{offer_id}/applicants/{applicant_id}")
    suspend fun updateApplicantStatus(
        @Header("Authorization") token: String,
        @Path("offer_id") offerId: String,
        @Path("applicant_id") applicantId: String,
        @Body payload: UpdateApplicantStatusPayload
    ): Response<UpdateApplicantStatusResponse>
}