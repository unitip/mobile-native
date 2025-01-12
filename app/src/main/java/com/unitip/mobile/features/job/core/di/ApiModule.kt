package com.unitip.mobile.features.job.core.di

import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun providerJobApi(): JobApi = ApiConfig.retrofit.create(JobApi::class.java)
}