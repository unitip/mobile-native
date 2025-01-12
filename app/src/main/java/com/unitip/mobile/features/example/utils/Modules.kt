package com.unitip.mobile.features.example.utils

import com.unitip.mobile.features.example.data.sources.ExampleApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object Modules {
    @Provides
    fun provideExampleApi(): ExampleApi = ApiConfig.retrofit.create(ExampleApi::class.java)
}