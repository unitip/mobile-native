package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.commons.JobConstant
import com.unitip.mobile.features.job.domain.models.JobModel
import com.unitip.mobile.network.openapi.apis.JobApi
import com.unitip.mobile.network.openapi.models.CreateJobApplicationRequest
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriverJobRepository @Inject constructor(
    private val jobApi: JobApi
) {
    suspend fun createApplication(
        jobId: String,
        bidPrice: Int,
        bidNote: String
    ): Either<Failure, Unit> = try {
        val response = jobApi.createJobApplication(
            jobId = jobId,
            CreateJobApplicationRequest(
                price = bidPrice,
                bidNote = bidNote
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

    suspend fun getAll(): Either<Failure, List<JobModel.ListItem>> = try {
        val response = jobApi.getAllJobs()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.jobs.map { job ->
                JobModel.ListItem(
                    id = job.id,
                    note = job.note,
                    pickupLocation = job.pickupLocation,
                    destinationLocation = job.destinationLocation,
                    service = JobConstant.Service.entries[job.service.ordinal],
                    createdAt = job.createdAt,
                    updatedAt = job.updatedAt,
                    customer = JobModel.ListItem.Customer(
                        name = job.customer.name,
                    )
                )
            })

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun get(
        jobId: String
    ): Either<Failure, Unit> = try {
        val response = jobApi.getJobForDriver(
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}