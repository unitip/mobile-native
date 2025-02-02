package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.ApplyJobResponse
import com.unitip.mobile.features.job.data.dtos.ApplySingleJobPayload
import com.unitip.mobile.features.job.data.dtos.ApproveApplicantResponse
import com.unitip.mobile.features.job.data.dtos.CreateJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateJobResponse
import com.unitip.mobile.features.job.data.dtos.GetJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@Deprecated("Gunakan JobApi")
interface SingleJobApi {
    @POST("jobs/single")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateJobPayload
    ): Response<CreateJobResponse>

    @GET("jobs/single/{job_id}")
    suspend fun get(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Query("type") type: String
    ): Response<GetJobResponse>

    @POST("jobs/single/{job_id}/applications")
    suspend fun apply(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Body payload: ApplySingleJobPayload
    ): Response<ApplyJobResponse>

    @PATCH("jobs/single/{job_id}/applications/{application_id}/approval")
    suspend fun approve(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Path("application_id") applicationId: String,
    ): Response<ApproveApplicantResponse>

}