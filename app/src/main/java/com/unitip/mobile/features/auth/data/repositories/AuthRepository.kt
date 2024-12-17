package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.auth.data.models.LoginPayload
import com.unitip.mobile.features.auth.data.models.LoginResult
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.providers.Preferences
import com.unitip.mobile.shared.utils.ApiError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val preferences: Preferences,
) {
    suspend fun login(
        email: String,
        password: String,
    ): Either<Failure, LoginResult> {
        val response = authApi.login(LoginPayload(email, password))
        val result = response.body()

        return if (response.isSuccessful && result != null)
            Either.Right(
                LoginResult(
                    needRole = result.needRole,
                    roles = result.roles,
                )
            )
        else Either.Left(ApiError.mapToFailure(response))
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ) {

    }
}