package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.ApplyJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateJobPayload
import com.unitip.mobile.features.job.data.models.CreateJobResult
import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.features.job.domain.models.Applicant
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
    private val jobApi: JobApi,
) {
    suspend fun create(
        title: String,
        note: String,
        pickupLocation: String,
        destination: String,
        type: String,
    ): Either<Failure, CreateJobResult> {
        try {
            val token = sessionManager.read()?.token
            val response = jobApi.create(
                token = "Bearer $token",
                payload = CreateJobPayload(
                    title = title,
                    note = note,
                    pickupLocation = pickupLocation,
                    destination = destination,
                    type = type,
                )
            )
            val result = response.body()

            return when (response.isSuccessful && result != null) {
                true -> Either.Right(CreateJobResult(id = result.id))
                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

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
                                type = it.type,
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

    suspend fun get(id: String, type: String): Either<Failure, Job> {
        try {
            val token = sessionManager.read()?.token
            val response = jobApi.get(token = "Bearer $token", jobId = id, type = type)
            val result = response.body()

            return when (response.isSuccessful && result != null) {
                true -> Either.Right(
                    Job(
                        id = result.id,
                        title = result.title,
                        note = result.note,
                        pickupLocation = result.pickupLocation,
                        destination = result.destination,
                        type = result.type,
                        customer = JobCustomer(name = "no data"),
                        applicants = result.applicants.map {
                            Applicant(
                                id = it.id,
                                name = it.name,
                                price = it.price
                            )
                        },
                    )
                )

                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun apply(jobId: String, price: Int): Either<Failure, Unit> {
        try {
            val token = sessionManager.read()?.token
            val response =
                jobApi.apply(
                    token = "Bearer $token",
                    jobId = jobId,
                    payload = ApplyJobPayload(price = price)
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