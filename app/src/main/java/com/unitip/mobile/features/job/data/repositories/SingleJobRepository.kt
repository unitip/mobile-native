package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.CreateSingleJobPayload
import com.unitip.mobile.features.job.data.models.CreateJobResult
import com.unitip.mobile.features.job.data.sources.SingleJobApi
import com.unitip.mobile.features.job.domain.models.Applicant
import com.unitip.mobile.features.job.domain.models.Job
import com.unitip.mobile.features.job.domain.models.JobCustomer
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleJobRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val singleJobApi: SingleJobApi
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
            val response = singleJobApi.create(
                token = "Bearer $token",
                payload = CreateSingleJobPayload(
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

    suspend fun get(id: String, type: String): Either<Failure, Job> {
        try {
            val token = sessionManager.read()?.token
            val response = singleJobApi.get(token = "Bearer $token", jobId = id, type = type)
            val result = response.body()

            return when (response.isSuccessful && result != null) {
                true -> Either.Right(
                    Job(
                        id = result.id,
                        title = result.title,
                        note = result.note,
                        pickupLocation = result.pickupLocation,
                        destination = result.destination,
                        service = result.service,
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

}