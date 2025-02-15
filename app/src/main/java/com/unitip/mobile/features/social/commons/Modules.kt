package com.unitip.mobile.features.social.commons

import com.unitip.mobile.network.openapi.apis.SocialApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun provideSocialApi(): SocialApi = ApiConfig.newRetrofit.create(SocialApi::class.java)
}
