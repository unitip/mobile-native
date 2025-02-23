package com.unitip.mobile.features.activity.data.repositories

import arrow.core.Either
import com.unitip.mobile.network.openapi.apis.ActivityApi
import com.unitip.mobile.network.openapi.models.CreateActivityRequest
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepository @Inject constructor(
    private val activityApi: ActivityApi
) {
    suspend fun createActivity(
        content: String
    ): Either<Failure, Unit> = try {
        val response = activityApi.createActivity(
            CreateActivityRequest(content = content)
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(Unit)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}