package com.unitip.mobile.features.auth.data.sources

import com.unitip.mobile.features.auth.data.dtos.LoginPayload
import com.unitip.mobile.features.auth.data.dtos.LoginResponse
import com.unitip.mobile.features.auth.data.dtos.RegisterPayload
import com.unitip.mobile.features.auth.data.dtos.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body payload: LoginPayload): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body payload: RegisterPayload): Response<RegisterResponse>
}