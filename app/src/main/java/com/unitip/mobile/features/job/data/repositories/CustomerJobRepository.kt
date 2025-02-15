package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.commons.JobConstant
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
        note: String,
        pickupLocation: String,
        destinationLocation: String,
        service: JobConstant.Service,
        expectedPrice: Int
    ): Either<Failure, Unit> = try {
        val response = jobApi.createJob(
            CreateJobRequest(
                note = note,
                pickupLocation = pickupLocation,
                destinationLocation = destinationLocation,
                service = when (service) {
                    JobConstant.Service.AntarJemput -> CreateJobRequest.Service.AntarJemput
                    JobConstant.Service.JasaTitip -> CreateJobRequest.Service.JasaTitip
                },
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
                    note = result.note,
                    pickupLocation = result.pickupLocation,
                    destinationLocation = result.destinationLocation,
                    expectedPrice = result.expectedPrice,
                    price = result.price,
                    service = JobConstant.Service.entries[result.service.ordinal],
                    status = JobConstant.Status.entries[result.status.ordinal],
                    applications = result.applications.map { application ->
                        GetResult.Application(
                            id = application.id,
                            bidPrice = application.bidPrice,
                            bidNote = application.bidNote,
                            driver = GetResult.Application.Driver(
                                name = application.driver.name
                            )
                        )
                    },
                    driver = when (result.driver != null) {
                        true -> GetResult.Driver(
                            id = result.driver.id,
                            name = result.driver.name
                        )

                        else -> null
                    }
                )
            )

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}