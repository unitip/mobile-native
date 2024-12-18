package com.unitip.mobile.shared.core.helpers

import com.google.gson.Gson
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.data.providers.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val preferences: Preferences,
) {
    companion object {
        private const val KEY = "com.unitip.mobile.SESSION"
    }

    private val gson = Gson()

    fun create(session: Session) =
        preferences.instance().edit().putString(KEY, gson.toJson(session)).apply()

    fun read(): Session? = preferences.instance().getString(KEY, null)?.let {
        gson.fromJson(it, Session::class.java)
    }

    fun delete() = preferences.instance().edit().remove(KEY).apply()
}