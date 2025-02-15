package com.unitip.mobile.features.account.commons

import com.unitip.mobile.features.account.data.sources.AccountApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideAccountApi(): AccountApi = ApiConfig.newRetrofit.create((AccountApi::class.java))
}