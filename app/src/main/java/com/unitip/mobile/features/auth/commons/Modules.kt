package com.unitip.mobile.features.auth.commons

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
//    @Provides
//    fun provideAuthApi(): AuthApi =
//        ApiConfig.newRetrofit.create(AuthApi::class.java)
}