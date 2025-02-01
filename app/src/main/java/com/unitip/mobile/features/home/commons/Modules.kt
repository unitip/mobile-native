package com.unitip.mobile.features.home.commons

import com.unitip.mobile.features.home.data.sources.CustomerOrderApi
import com.unitip.mobile.features.home.data.sources.DriverOrderApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideCustomerOrderApi(): CustomerOrderApi =
        ApiConfig.retrofit.create(CustomerOrderApi::class.java)

    @Provides
    fun provideDriverOrderApi(): DriverOrderApi =
        ApiConfig.retrofit.create(DriverOrderApi::class.java)
}