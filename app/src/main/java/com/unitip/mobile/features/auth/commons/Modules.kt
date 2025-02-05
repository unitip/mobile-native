package com.unitip.mobile.features.auth.commons

import com.unitip.mobile.network.openapi.apis.AuthApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideAuthApi(): AuthApi =
        ApiConfig.newRetrofit.create(AuthApi::class.java)
}