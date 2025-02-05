package com.unitip.mobile.shared.commons

import android.app.Application
import com.unitip.mobile.shared.commons.configs.ApiConfig
import com.unitip.mobile.shared.data.managers.SessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class UnitipApplication : Application() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()

        ApiConfig.refreshToken(
            token = sessionManager.getToken()
        )
    }
}