package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import com.unitip.mobile.core.failure.Failure
import com.unitip.mobile.features.auth.data.models.LoginPayload
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.data.providers.Preferences
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
    ): Either<Failure, Boolean> {
        val response = authApi.login(LoginPayload(email, password))
        if (!response.isSuccessful)
            return Either.Left(Failure(message = response.message()))

        // save session to local storage

        return Either.Right(true)
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ) {

    }
}