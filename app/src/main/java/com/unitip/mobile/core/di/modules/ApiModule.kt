package com.unitip.mobile.core.di.modules

import com.unitip.mobile.core.config.ApiConfig
import com.unitip.mobile.features.auth.data.sources.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideAuthApiService(): AuthApi = ApiConfig.retrofit.create(AuthApi::class.java)
}