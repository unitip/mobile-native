package com.unitip.mobile.features.account.data.sources

import com.unitip.mobile.features.account.data.dtos.LogoutResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header

interface AuthApi {
    @DELETE("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<LogoutResponse>
}