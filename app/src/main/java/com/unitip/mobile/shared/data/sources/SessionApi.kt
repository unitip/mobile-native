package com.unitip.mobile.shared.data.sources

import retrofit2.http.GET

interface SessionApi {
    @GET("/auth/logout")
    suspend fun logout()
}