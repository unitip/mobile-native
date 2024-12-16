package com.unitip.mobile.features.auth.data.sources

import com.unitip.mobile.features.auth.data.models.AuthResponse
import com.unitip.mobile.features.auth.data.models.LoginPayload
import com.unitip.mobile.features.auth.data.models.RegisterPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body payload: LoginPayload): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body payload: RegisterPayload): Response<AuthResponse>

}