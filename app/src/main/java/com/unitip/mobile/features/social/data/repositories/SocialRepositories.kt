package com.unitip.mobile.features.social.data.repositories

import android.content.Context
import android.util.Log
import arrow.core.Either
import com.unitip.mobile.features.social.commons.SocialActivityCache
import com.unitip.mobile.features.social.data.source.SocialApi
import com.unitip.mobile.features.social.domain.models.SocialActivity
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
    private val socialApi: SocialApi,
    @ApplicationContext private val context: Context
) {
    suspend fun getSocialActivities(): Either<Failure, List<SocialActivity>> = try {
        // Cek cache terlebih dahulu
        val cachedData = SocialActivityCache.get()
        val lastModified = SocialActivityCache.getLastModified(context)

        val response = socialApi.getSocial(lastModified)

        when {
            response.code() == 304 -> {
                // Data tidak berubah, gunakan cache
                Log.d("SocialRepository", "Using old cache data")
                cachedData?.let {
                    Either.Right(it)
                } ?: fetchFresh() // Jika cache kosong, fetch baru
            }
            response.isSuccessful && response.body() != null -> {
                val result = response.body()!!
                val activities = result.activities.map {
                    SocialActivity(
                        censoredName = it.censoredName,
                        activityType = it.activityType,
                        timeAgo = it.timeAgo
                    )
                }
                response.headers()["last-modified"]?.let { newLastModified ->
                    Log.d("SocialRepository", "New Last-Modified: $newLastModified")
                    SocialActivityCache.set(context, activities, newLastModified)
                }
                Log.d("SocialRepository", "Fetched new data and updated cache")
                Either.Right(activities)
            }
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Log.d("SocialRepository", "error but getSocialActivities: ${e.message}")
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    private suspend fun fetchFresh(): Either<Failure, List<SocialActivity>> {
        return getSocialActivities()
    }
}