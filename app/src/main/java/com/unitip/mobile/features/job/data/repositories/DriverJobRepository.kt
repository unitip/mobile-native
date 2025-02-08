package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
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
    suspend fun createApplication(): Either<Failure, Unit> = try {
        val response = jobApi.createJobApplication(CreateJobApplicationRequest())
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun getAll(): Either<Failure, Unit> = try {
        val response = jobApi.getAllJobs()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun get(): Either<Failure, Unit> = try {
        val response = jobApi.getJob()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}