package com.unitip.mobile.shared.data.providers

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(
    private val application: Application,
) {
    fun instance() = application.getSharedPreferences(
        "com.unitip.mobile.shared.data.providers.Preferences",
        Application.MODE_PRIVATE,
    )
}