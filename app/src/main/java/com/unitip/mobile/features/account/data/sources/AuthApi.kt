package com.unitip.mobile.features.account.data.sources

import com.unitip.mobile.features.account.data.dtos.LogoutResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header

interface AuthApi {
    @DELETE("api/v1/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<LogoutResponse>
}