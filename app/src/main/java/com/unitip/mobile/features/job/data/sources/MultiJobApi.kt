package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.CreateMultiJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateMultiJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

@Deprecated("Gunakan JobApi")
interface MultiJobApi {
    @POST("jobs/multi")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body payload: CreateMultiJobPayload
    ): Response<CreateMultiJobResponse>
}