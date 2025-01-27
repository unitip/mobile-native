package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.GetAllJobsResponse
import com.unitip.mobile.features.job.data.dtos.GetJobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface JobApi {
    @GET("jobs")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Response<GetAllJobsResponse>

    @GET("jobs/{job_id}")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("job_id") jobId: String
    ): Response<GetJobResponse>
}