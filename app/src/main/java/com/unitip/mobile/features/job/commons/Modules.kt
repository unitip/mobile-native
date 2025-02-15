package com.unitip.mobile.features.job.commons

import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideJobApi(): JobApi =
        ApiConfig.retrofit.create(JobApi::class.java)
}