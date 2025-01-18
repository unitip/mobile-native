package com.unitip.mobile.features.offer.commons

import com.unitip.mobile.features.job.data.sources.JobApi
import com.unitip.mobile.features.offer.data.sources.OfferApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun providerOfferApi(): OfferApi = ApiConfig.retrofit.create(OfferApi::class.java)
}