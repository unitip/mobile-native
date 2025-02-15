package com.unitip.mobile.features.social.data.repositories

import android.util.Log
import arrow.core.Either
import com.unitip.mobile.features.social.domain.models.SocialActivity
import com.unitip.mobile.network.openapi.apis.SocialApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject

class SocialRepository @Inject constructor(
    private val socialApi: SocialApi
) {
    suspend fun getSocialActivities(): Either<Failure, List<SocialActivity>> = try {
        val response = socialApi.getSocial()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                result.activities?.map {
                    SocialActivity(
                        censoredName = it.censoredName ?: "",
                        activityType = it.activityType ?: "",
                        timeAgo = it.timeAgo ?: ""
                    )
                } ?: emptyList()
            )

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Log.d("SocialRepository", "getSocialActivities: ${e.message}")
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}