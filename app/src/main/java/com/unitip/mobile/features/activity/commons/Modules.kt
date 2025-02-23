package com.unitip.mobile.features.activity.commons

import com.unitip.mobile.features.activity.data.source.SocialApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideSocialApi(): SocialApi = ApiConfig.retrofit.create(SocialApi::class.java)
}
