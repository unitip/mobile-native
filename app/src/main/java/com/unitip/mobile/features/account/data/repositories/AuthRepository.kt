package com.unitip.mobile.features.account.data.repositories

import arrow.core.Either
import com.unitip.mobile.network.openapi.apis.AuthApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
) {
    suspend fun logout(): Either<Failure, Unit> = try {
        val response = authApi.logout()
        val result = response.body()

        when ((response.isSuccessful && result != null) || response.code() == 401) {
            true -> {
                sessionManager.delete()
                Either.Right(Unit)
            }

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}