package com.unitip.mobile.features.social.data.repositories

import android.util.Log
import arrow.core.Either
import com.unitip.mobile.features.social.commons.SocialActivityCache
import com.unitip.mobile.features.social.domain.models.SocialActivity
import com.unitip.mobile.network.openapi.apis.SocialApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject

class SocialRepository @Inject constructor(
    private val socialApi: SocialApi
) {
    suspend fun getSocialActivities(): Either<Failure, List<SocialActivity>> = try {
        // Cek cache terlebih dahulu
        SocialActivityCache.get()?.let { cachedData ->
            return Either.Right(cachedData)
        }

        // Jika tidak ada cache, lakukan request API
        val response = socialApi.getSocial()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                val activities = result.activities?.map {
                    SocialActivity(
                        censoredName = it.censoredName ?: "",
                        activityType = it.activityType ?: "",
                        timeAgo = it.timeAgo ?: ""
                    )
                } ?: emptyList()

                // Simpan ke cache
                SocialActivityCache.set(activities)

                Either.Right(activities)
            }
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Log.d("SocialRepository", "getSocialActivities: ${e.message}")
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}