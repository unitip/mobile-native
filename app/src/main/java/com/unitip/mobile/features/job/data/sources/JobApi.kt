package com.unitip.mobile.features.job.data.sources

import com.unitip.mobile.features.job.data.dtos.ApplyJobPayload
import com.unitip.mobile.features.job.data.dtos.ApplyJobResponse
import com.unitip.mobile.features.job.data.dtos.ApproveApplicantResponse
import com.unitip.mobile.features.job.data.dtos.GetAllJobsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JobApi {


    @GET("jobs")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Response<GetAllJobsResponse>


    @POST("jobs/{job_id}/apply")
    suspend fun apply(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Body payload: ApplyJobPayload
    ): Response<ApplyJobResponse>

    @GET("jobs/{job_id}/applicants/{applicant_id}/approve")
    suspend fun approve(
        @Header("Authorization") token: String,
        @Path("job_id") jobId: String,
        @Path("applicant_id") applicantId: String,
    ): Response<ApproveApplicantResponse>
}