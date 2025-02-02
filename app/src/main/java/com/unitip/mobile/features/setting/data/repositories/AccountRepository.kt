package com.unitip.mobile.features.setting.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.setting.data.dtos.EditPasswordPayload
import com.unitip.mobile.features.setting.data.dtos.EditPayload
import com.unitip.mobile.features.setting.data.sources.AccountApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountApi: AccountApi,
    private val sessionManager: SessionManager


) {
    private val session = sessionManager.read()
    suspend fun editProfile(
        name: String,
        gender: String,
    ): Either<Failure, Boolean> {
        try {
            val token = session.token

            val response = accountApi.editProfile(
                token = "Bearer $token",
                payload = EditPayload(name = name, gender = gender)
            )
            val result = response.body()

            if (response.isSuccessful && result != null) {

                return Either.Right(true)
            }
            return Either.Left(response.mapToFailure())
        } catch (
            e: Exception
        ) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun editPassword(password: String): Either<Failure, Boolean> {
        try {
            val token = session.token

            val response = accountApi.editPassword(
                token = "Bearer $token",
                payload = EditPasswordPayload(password = password),
            )
            val result = response.body()

            if (response.isSuccessful && result != null) {
                return Either.Right(true)
            }

            return Either.Left(response.mapToFailure())
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }
}