package com.unitip.mobile.features.home.data.sources

import com.unitip.mobile.features.home.data.dtos.GetAllCustomerOrdersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CustomerOrderApi {
    @GET("accounts/customer/orders")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Response<GetAllCustomerOrdersResponse>
}