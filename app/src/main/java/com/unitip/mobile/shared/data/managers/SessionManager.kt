package com.unitip.mobile.shared.data.managers

import com.google.gson.Gson
import com.unitip.mobile.shared.data.providers.PreferencesProvider
import com.unitip.mobile.shared.domain.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    preferencesProvider: PreferencesProvider,
) {
    companion object {
        private const val KEY = "com.unitip.mobile.session"
    }

    private val gson = Gson()
    private val preferences = preferencesProvider.client
    
    fun isAuthenticated(): Boolean =
        preferences.getString(KEY, null) != null

    fun create(session: Session) =
        preferences.edit().putString(KEY, gson.toJson(session)).apply()

    fun read(): Session = preferences.getString(KEY, null).let {
        when (!it.isNullOrBlank()) {
            true -> gson.fromJson(it, Session::class.java)
            else -> Session()
        }
    }

    fun delete() = preferences.edit().remove(KEY).apply()
}