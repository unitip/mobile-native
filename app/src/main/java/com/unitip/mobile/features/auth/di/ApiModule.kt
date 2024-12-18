package com.unitip.mobile.features.auth.di

import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.shared.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideAuthApi(): AuthApi = ApiConfig.retrofit.create(AuthApi::class.java)
}