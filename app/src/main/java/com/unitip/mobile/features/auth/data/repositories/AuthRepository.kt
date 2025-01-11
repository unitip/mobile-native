package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.auth.data.dtos.LoginPayload
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.features.auth.domain.models.LoginResult
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.utils.extensions.mapToFailure
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
        role: String? = null
    ): Either<Failure, LoginResult> {
        try {
            val response =
                authApi.login(
                    LoginPayload(
                        email = email,
                        password = password,
                        role = role
                    )
                )
            val result = response.body()

            if (response.isSuccessful && result != null) {
                if (!result.needRole)
                    sessionManager.create(
                        Session(
                            name = result.name,
                            email = result.email,
                            token = result.token,
                            role = result.role,
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