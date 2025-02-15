package com.unitip.mobile.features.social.data.source

import com.unitip.mobile.features.social.data.dtos.SocialResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SocialApi {
    @GET("/api/v1/social")
    suspend fun getSocial(
        @Header("if-modified-since") ifModifiedSince: String? = null
    ): Response<SocialResponse>
}