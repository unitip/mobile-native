package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.ApplyJobResponse
import com.unitip.mobile.features.job.data.dtos.CreateJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateJobResponse
import com.unitip.mobile.features.job.data.dtos.GetAllJobsResponse
import com.unitip.mobile.features.job.data.dtos.GetJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JobApi {
    @POST("jobs")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateJobPayload
    ): Response<CreateJobResponse>

    @GET("jobs")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Response<GetAllJobsResponse>

    @GET("jobs/{job_id}")
    suspend fun get(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String
    ): Response<GetJobResponse>

    @PATCH("jobs/{job_id}/applications")
    suspend fun apply(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String
    ): Response<ApplyJobResponse>
}