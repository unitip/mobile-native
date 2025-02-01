package com.unitip.mobile.features.home.commons

import com.unitip.mobile.features.home.data.sources.OrderApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideOrderApi(): OrderApi =
        ApiConfig.retrofit.create(OrderApi::class.java)
}