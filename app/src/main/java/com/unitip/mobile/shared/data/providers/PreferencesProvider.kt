package com.unitip.mobile.shared.data.providers

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesProvider @Inject constructor(
    application: Application,
) {
    companion object {
        private const val NAME = "com.unitip.mobile.preferences"
    }

    val client: SharedPreferences = application.getSharedPreferences(
        NAME, Application.MODE_PRIVATE,
    )
}