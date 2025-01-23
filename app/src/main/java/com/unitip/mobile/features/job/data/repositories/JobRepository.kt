package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.ApplicationSingleJobPayload
import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.features.job.domain.models.GetAllJobsResult
import com.unitip.mobile.features.job.domain.models.Job
import com.unitip.mobile.features.job.domain.models.JobCustomer
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val jobApi: JobApi
) {

    suspend fun getAll(): Either<Failure, GetAllJobsResult> {
        try {
            val token = sessionManager.read()?.token
            val response = jobApi.getAll(token = "Bearer $token")
            val result = response.body()

            return when (response.isSuccessful && result != null) {
                true -> Either.Right(
                    GetAllJobsResult(
                        jobs = result.jobs.map {
                            Job(
                                id = it.id,
                                title = it.title,
                                note = it.note,
                                service = it.service,
                                pickupLocation = it.pickupLocation,
                                destination = it.destination,
                                customer = JobCustomer(
                                    name = it.customer.name
                                )
                            )
                        },
                        hasNext = result.pageInfo.page < result.pageInfo.totalPages
                    )
                )

                false -> Either.Left(response.mapToFailure())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }


    suspend fun approve(jobId: String, applicantId: String): Either<Failure, Unit> {
        try {
            val token = sessionManager.read()?.token
            val response =
                jobApi.approve(
                    token = "Bearer $token",
                    jobId = jobId,
                    applicantId = applicantId,
                )
            val result = response.body()
            return when (response.isSuccessful && result != null) {
                true -> Either.Right(Unit)
                false -> Either.Left(response.mapToFailure())
            }

        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }
}