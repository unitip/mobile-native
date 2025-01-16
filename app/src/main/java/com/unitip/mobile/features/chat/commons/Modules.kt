package com.unitip.mobile.features.chat.commons

import com.unitip.mobile.features.chat.data.sources.ChatApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object Modules {
    @Provides
    fun provideChatApi(): ChatApi = ApiConfig.retrofit.create(ChatApi::class.java)
}