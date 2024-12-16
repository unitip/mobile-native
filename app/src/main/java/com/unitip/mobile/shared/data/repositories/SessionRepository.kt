package com.unitip.mobile.shared.data.repositories

import arrow.core.Either
import com.unitip.mobile.core.failure.Failure
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.data.sources.Preferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val preferences: Preferences,
) {
    companion object {
        private const val SESSION_KEY = "com.unitip.mobile.SESSION"
    }

    fun create(
        name: String,
        email: String,
        token: String,
    ): Either<Failure, Boolean> =
        try {
            preferences.instance().edit().putString(
                SESSION_KEY, Json.encodeToString(
                    Session(
                        name = name,
                        email = email,
                        token = token
                    )
                )
            ).apply()
            Either.Right(true)
        } catch (e: Exception) {
            Either.Left(
                Failure(
                    message = e.message ?: "Something went wrong, please try again later!"
                )
            )
        }

    fun read(): Either<Failure, Session> =
        when (val sessionStr = preferences.instance().getString(SESSION_KEY, null)) {
            null -> Either.Left(Failure(message = "session-not-found"))
            else -> Either.Right(Json.decodeFromString<Session>(sessionStr))
        }


    fun logout(): Either<Failure, Boolean> {
        return Either.Right(false)
    }
}