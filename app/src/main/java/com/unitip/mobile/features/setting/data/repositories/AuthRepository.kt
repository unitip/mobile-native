package com.unitip.mobile.features.setting.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.setting.data.sources.AuthApi
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.helper.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
) {
    suspend fun logout(): Either<Failure, Boolean> {
        try {
            val token = sessionManager.read()?.token

            val response = authApi.logout(token = "Bearer $token")
            val result = response.body()

            return Either.Right(true)
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }
}