package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.ApplicationSingleJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateSingleJobPayload
import com.unitip.mobile.features.job.data.sources.SingleJobApi
import com.unitip.mobile.features.job.domain.models.Applicant
import com.unitip.mobile.features.job.domain.models.Job
import com.unitip.mobile.features.job.domain.models.JobCustomer
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleJobRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val singleJobApi: SingleJobApi
) {
    private val session = sessionManager.read()

    suspend fun create(
        title: String,
        note: String,
        pickupLocation: String,
        destination: String,
        service: String,
    ): Either<Failure, Unit> = try {
        val response = singleJobApi.create(
            token = "Bearer ${session.token}",
            payload = CreateSingleJobPayload(
                title = title,
                note = note,
                pickupLocation = pickupLocation,
                destination = destination,
                service = service,
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun get(jobId: String, service: String): Either<Failure, Job> = try {
        val response = singleJobApi.get(
            token = "Bearer ${session.token}",
            jobId = jobId,
            type = service
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
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
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun application(jobId: String, price: Int): Either<Failure, Unit> {
        try {
            val response =
                singleJobApi.apply(
                    token = "Bearer ${session.token}",
                    jobId = jobId,
                    payload = ApplicationSingleJobPayload(price = price)
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

    suspend fun approve(jobId: String, applicantId: String): Either<Failure, Unit> {
        try {
            val response =
                singleJobApi.approve(
                    token = "Bearer ${session.token}",
                    jobId = jobId,
                    applicationId = applicantId,
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

