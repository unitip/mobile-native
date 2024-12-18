package com.unitip.mobile.shared.data.repositories

import arrow.core.Either
import com.google.gson.Gson
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.data.providers.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("Use SessionManager from shared module for simple session management")
@Singleton
class SessionRepository @Inject constructor(
    private val preferences: Preferences,
) {
    private val gson = Gson()

    companion object {
        private const val SESSION_KEY = "com.unitip.mobile.SESSION"
    }

    fun create(
        name: String,
        email: String,
        token: String,
    ): Either<Failure, Session> =
        try {
            val session = Session(
                name = name,
                email = email,
                token = token
            )
            preferences.instance().edit().putString(SESSION_KEY, gson.toJson(session)).apply()
            Either.Right(session)
        } catch (e: Exception) {
            Either.Left(
                Failure(
                    message = e.message ?: "Something went wrong, please try again later!"
                )
            )
        }

    fun read(): Either<Failure, Session> =
        when (val sessionStr = preferences.instance().getString(SESSION_KEY, null)) {
            null -> Either.Left(Failure(message = "Session tidak ditemukan!"))
            else -> Either.Right(gson.fromJson(sessionStr, Session::class.java))
        }


    fun logout(): Either<Failure, Boolean> {
        return Either.Right(false)
    }
}