package com.unitip.mobile.shared.data.sources

import com.unitip.mobile.shared.data.dto.GetAllPricesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("services/prices")
    suspend fun getAllPrices(): Response<GetAllPricesResponse>
}