package com.unitip.mobile.features.location.commons

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideFusedLocation(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)
}