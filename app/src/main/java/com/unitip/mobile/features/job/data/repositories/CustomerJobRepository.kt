package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.network.openapi.apis.JobApi
import com.unitip.mobile.network.openapi.models.CreateJobRequest
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerJobRepository @Inject constructor(
    private val jobApi: JobApi
) {
    suspend fun create(
        title: String,
        destinationLocation: String,
        note: String,
        service: String,
        pickupLocation: String,
        expectedPrice: Int
    ): Either<Failure, Unit> = try {
        val response = jobApi.createJob(
            CreateJobRequest(
                title = title,
                destinationLocation = destinationLocation,
                note = note,
                service = when (service) {
                    "antar-jemput" -> CreateJobRequest.Service.AntarJemput
                    else -> CreateJobRequest.Service.JasaTitip
                },
                pickupLocation = pickupLocation,
                expectedPrice = expectedPrice
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun get(
        jobId: String
    ): Either<Failure, Unit> = try {
        val response = jobApi.getJobForCustomer()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}