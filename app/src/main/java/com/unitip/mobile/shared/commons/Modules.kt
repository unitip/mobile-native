package com.unitip.mobile.shared.commons

import com.unitip.mobile.network.openapi.apis.AccountApi
import com.unitip.mobile.network.openapi.apis.AuthApi
import com.unitip.mobile.network.openapi.apis.JobApi
import com.unitip.mobile.network.openapi.apis.OfferApi
import com.unitip.mobile.shared.commons.configs.ApiConfig
import com.unitip.mobile.shared.data.sources.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideServiceApi(): ServiceApi =
        ApiConfig.retrofit.create(ServiceApi::class.java)

    /**
     * karena menggunakan generator openapi, maka provide service api harus
     * dilakukan di shared module. hal ini karena terdapat beberapa kasus
     * dimana berbeda fitur tetapi membutuhkan service api yang sama. serta
     * service api (hasil generate) sekarang sudah bernilai global
     */
    @Provides
    fun provideAuthApi(): AuthApi =
        ApiConfig.newRetrofit.create(AuthApi::class.java)

    @Provides
    fun provideAccountApi(): AccountApi =
        ApiConfig.newRetrofit.create(AccountApi::class.java)

    @Provides
    fun provideJobApi(): JobApi =
        ApiConfig.newRetrofit.create(JobApi::class.java)

    @Provides
    fun provideOfferApi(): OfferApi =
        ApiConfig.newRetrofit.create(OfferApi::class.java)
}