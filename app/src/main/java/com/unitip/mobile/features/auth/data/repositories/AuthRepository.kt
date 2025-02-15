package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.auth.domain.models.LoginResult
import com.unitip.mobile.network.openapi.apis.AuthApi
import com.unitip.mobile.network.openapi.models.LoginRequest
import com.unitip.mobile.network.openapi.models.RegisterRequest
import com.unitip.mobile.shared.commons.configs.ApiConfig
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import com.unitip.mobile.shared.domain.models.Session
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
    ): Either<Failure, LoginResult> = try {
        val response = authApi.login(
            LoginRequest(
                email = email,
                password = password,
                role = role
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                if (!result.needRole)
                    sessionManager.create(
                        Session(
                            id = result.id,
                            name = result.name,
                            email = result.email,
                            token = result.token,
                            role = result.role,
                        )
                    )

                /**
                 * ketika berhasil login, maka refresh token yang akan dikirimkan ke server
                 * melalui http interceptor
                 */
                ApiConfig.refreshToken(token = result.token)

                Either.Right(
                    LoginResult(
                        needRole = result.needRole,
                        roles = result.roles,
                    )
                )
            }

            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Either<Failure, Unit> = try {
        // melakukan pengiriman registrasi ke server
        val response = authApi.register(
            RegisterRequest(
                name = name,
                email = email,
                password = password
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                sessionManager.create(
                    Session(
                        id = result.id,
                        name = result.name,
                        email = result.email,
                        token = result.token,
                        role = result.role,
                    )
                )

                /**
                 * ketika berhasil register, maka refresh token yang akan dikirimkan ke server
                 * melalui http interceptor
                 */
                ApiConfig.refreshToken(token = result.token)

                Either.Right(Unit)
            }

            false -> Either.Left(Failure(message = "Gagal melakukan Registrasi"))
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga"))
    }
}