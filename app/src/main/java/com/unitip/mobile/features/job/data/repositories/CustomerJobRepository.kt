package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.network.openapi.apis.JobApi
import com.unitip.mobile.network.openapi.models.CreateJobRequest
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton
import com.unitip.mobile.features.job.domain.models.DetailJobModel.ForCustomer as GetResult

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
    ): Either<Failure, GetResult> = try {
        val response = jobApi.getJobForCustomer(
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                GetResult(
                    id = result.id,
                    title = result.title,
                    note = result.note,
                    applications = result.applications.map { application ->
                        GetResult.Application(
                            id = application.id,
                            bidPrice = application.bidPrice,
                            bidNote = application.bidNote,
                            driver = GetResult.Application.Driver(
                                name = application.driver.name
                            )
                        )
                    }
                )
            )

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}