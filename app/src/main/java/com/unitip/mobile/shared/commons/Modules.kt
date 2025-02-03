package com.unitip.mobile.shared.commons

import com.unitip.mobile.shared.commons.configs.ApiConfig
import com.unitip.mobile.shared.data.sources.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideServiceApi(): ServiceApi =
        ApiConfig.retrofit.create(ServiceApi::class.java)
}