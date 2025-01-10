package com.unitip.mobile.features.example.data.sources

import com.unitip.mobile.features.example.data.dtos.GetAllUsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExampleApi {
    @GET("example")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>
}