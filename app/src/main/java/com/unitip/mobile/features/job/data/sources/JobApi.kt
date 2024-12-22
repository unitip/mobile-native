package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.CreateJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface JobApi {
    @POST("jobs")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateJobPayload
    ): Response<CreateJobResponse>
}