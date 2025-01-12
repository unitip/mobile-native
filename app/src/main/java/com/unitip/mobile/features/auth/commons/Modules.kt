package com.unitip.mobile.features.auth.commons

import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideAuthApi(): AuthApi = ApiConfig.retrofit.create(AuthApi::class.java)
}