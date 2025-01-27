package com.unitip.mobile.features.job.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.job.data.dtos.CreateMultiJobPayload
import com.unitip.mobile.features.job.data.sources.MultiJobApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("This class is deprecated, use JobRepository instead")
@Singleton
class MultiJobRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val multiJobApi: MultiJobApi
) {

    private val session = sessionManager.read()

    suspend fun create(
        title: String,
        note: String,
        pickupLocation: String,
        service: String,
    ): Either<Failure, Unit> = try {
        val response = multiJobApi.create(
            token = "Bearer ${session.token}",
            payload = CreateMultiJobPayload(
                title = title,
                note = note,
                pickupLocation = pickupLocation,
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
}