package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.commons.JobConstants
import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.features.job.domain.models.JobV2
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    sessionManager: SessionManager,
    private val jobApi: JobApi
) {
    private val session = sessionManager.read()

    suspend fun getAll(): Either<Failure, List<JobV2.ListItem>> = try {
        val response = jobApi.getAll(
            token = "Bearer ${session.token}"
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                result.jobs.map { job ->
                    JobV2.ListItem(
                        type = when (job.type == "single") {
                            true -> JobConstants.Type.SINGLE
                            else -> JobConstants.Type.MULTI
                        },
                        id = job.id,
                        title = job.title,
                        note = job.note,
                        service = job.service,
                        pickupLocation = job.pickupLocation,
                        destination = job.destination,
                        createdAt = job.createdAt,
                        updatedAt = job.updatedAt,
                        totalApplications = job.totalApplications,
                        customer = JobV2.ListItem.Customer(
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
}