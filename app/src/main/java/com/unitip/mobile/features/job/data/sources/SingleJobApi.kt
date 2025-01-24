package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.ApplicationSingleJobPayload
import com.unitip.mobile.features.job.data.dtos.ApplicationSingleJobResponse
import com.unitip.mobile.features.job.data.dtos.ApproveApplicantResponse
import com.unitip.mobile.features.job.data.dtos.CreateSingleJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateSingleJobResponse
import com.unitip.mobile.features.job.data.dtos.GetSingleJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SingleJobApi {
    @POST("jobs/single")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateSingleJobPayload
    ): Response<CreateSingleJobResponse>

    @GET("jobs/single/{job_id}")
    suspend fun get(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Query("type") type: String
    ): Response<GetSingleJobResponse>

    @POST("jobs/single/{job_id}/applications")
    suspend fun apply(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Body payload: ApplicationSingleJobPayload
    ): Response<ApplicationSingleJobResponse>

    @PATCH("jobs/single/{job_id}/applications/{application_id}/approval")
    suspend fun approve(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Path("application_id") applicationId: String,
    ): Response<ApproveApplicantResponse>

}