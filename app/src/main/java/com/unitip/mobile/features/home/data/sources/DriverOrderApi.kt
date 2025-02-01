package com.unitip.mobile.features.home.data.sources

import com.unitip.mobile.features.home.data.dtos.GetAllDriverOrdersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DriverOrderApi {
    @GET("accounts/driver/orders")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Response<GetAllDriverOrdersResponse>
}