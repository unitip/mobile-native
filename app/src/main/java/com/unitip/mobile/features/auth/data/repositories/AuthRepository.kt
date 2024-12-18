package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.auth.data.dtos.LoginPayload
import com.unitip.mobile.features.auth.data.models.LoginResult
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.extensions.mapToFailure
import com.unitip.mobile.shared.helper.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
) {
    suspend fun login(
        email: String,
        password: String,
    ): Either<Failure, LoginResult> {
        try {
            val response = authApi.login(LoginPayload(email, password))
            val result = response.body()

            if (response.isSuccessful && result != null) {
                sessionManager.create(
                    Session(
                        name = result.name,
                        email = result.email,
                        token = result.token
                    )
                )

                return Either.Right(
                    LoginResult(
                        needRole = result.needRole,
                        roles = result.roles,
                    )
                )
            }

            return Either.Left(response.mapToFailure())
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ) {

    }
}