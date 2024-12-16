package com.unitip.mobile.features.auth.data.repositories

import com.unitip.mobile.features.auth.data.models.LoginPayload
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.data.sources.Preferences
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
    ) {
        val response = authApi.login(LoginPayload(email, password))
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ) {

    }
}