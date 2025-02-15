package com.unitip.mobile.shared.commons.configs

import com.unitip.mobile.BuildConfig
import com.unitip.mobile.network.openapi.auth.HttpBearerAuth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private var httpBearerAuth = HttpBearerAuth(
        schema = "Bearer"
    )

    fun refreshToken(token: String) {
        httpBearerAuth.bearerToken = token
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpBearerAuth)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Deprecated("Gunakan newRetrofit karena sudah menggunakan baseurl sehingga compatible dengan codegen dari openapi")
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val newRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}