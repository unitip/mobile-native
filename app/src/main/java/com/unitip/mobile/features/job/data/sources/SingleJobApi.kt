package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.CreateSingleJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateSingleJobResponse
import com.unitip.mobile.features.job.data.dtos.GetSingleJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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
}