package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.ApplyJobPayload
import com.unitip.mobile.features.job.data.dtos.CreateJobPayload
import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.features.job.domain.models.JobApplicationModel
import com.unitip.mobile.features.job.domain.models.JobModel
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
    suspend fun create(
        title: String,
        destinationLocation: String,
        destinationLatitude: Double?,
        destinationLongitude: Double?,
        note: String,
        service: String,
        pickupLocation: String,
        pickupLatitude: Double?,
        pickupLongitude: Double?,
        expectedPrice: Int
    ): Either<Failure, Unit> = try {
        val response = jobApi.create(
            token = "Bearer ${sessionManager.getToken()}",
            payload = CreateJobPayload(
                title = title,
                destinationLocation = destinationLocation,
                destinationLatitude = destinationLatitude,
                destinationLongitude = destinationLongitude,
                note = note,
                service = service,
                pickupLocation = pickupLocation,
                pickupLatitude = pickupLatitude,
                pickupLongitude = pickupLongitude,
                expectedPrice = expectedPrice
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

    suspend fun getAll(): Either<Failure, List<JobModel.ListItem>> = try {
        val response = jobApi.getAll(
            token = "Bearer ${sessionManager.getToken()}"
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                result.jobs.map { job ->
                    JobModel.ListItem(
                        id = job.id,
                        title = job.title,
                        note = job.note,
                        service = job.service,
                        pickupLocation = job.pickupLocation,
                        destinationLocation = job.destinationLocation,
                        createdAt = job.createdAt,
                        updatedAt = job.updatedAt,
                        customer = JobModel.ListItem.Customer(
                            name = job.customer.name
                        )
                    )
                }
            )

            false -> Either.Left(response.mapToFailure())
        }

    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun get(jobId: String): Either<Failure, JobModel.Detail> = try {
        val response = jobApi.get(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                JobModel.Detail(
                    id = result.id,
                    title = result.title,
                    note = result.note,
                    service = result.service,
                    destinationLocation = result.destinationLocation,
                    destinationLatitude = result.destinationLatitude,
                    destinationLongitude = result.destinationLongitude,
                    pickupLocation = result.pickupLocation,
                    pickupLatitude = result.pickupLatitude,
                    pickupLongitude = result.pickupLongitude,
                    createdAt = result.createdAt,
                    updatedAt = result.updatedAt,
                    customer = JobModel.Detail.Customer(
                        id = result.customer.id,
                        name = result.customer.name
                    )
                )
            )

            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun createApplication(
        jobId: String,
        price: Int,
        bidNote: String
    ): Either<Failure, String> = try {
        val response = jobApi.createApplication(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId,
            payload = ApplyJobPayload(
                price = price,
                bidNote = bidNote
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.id)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun getAllApplications(
        jobId: String
    ): Either<Failure, List<JobApplicationModel>> = try {
        val response = jobApi.getAllApplications(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.applications.map { application ->
                JobApplicationModel(
                    id = application.id,
                    price = application.price,
                    bidNote = application.bidNote,
                    driver = JobApplicationModel.Driver(
                        name = application.driver.name
                    )
                )
            })

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun approveApplication(
        jobId: String,
        applicationId: String
    ): Either<Failure, String> = try {
        val response = jobApi.approveApplication(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId,
            applicationId = applicationId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.id)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun complete(jobId: String): Either<Failure, String> = try {
        val response = jobApi.complete(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.id)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun cancelApplication(jobId: String): Either<Failure, String> = try {
        val response = jobApi.cancelApplication(
            token = "Bearer ${sessionManager.getToken()}",
            jobId = jobId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.id)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}