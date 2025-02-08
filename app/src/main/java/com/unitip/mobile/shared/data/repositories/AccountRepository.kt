package com.unitip.mobile.shared.data.repositories

import arrow.core.Either
import com.unitip.mobile.network.openapi.apis.AccountApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import com.unitip.mobile.shared.domain.models.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val accountApi: AccountApi
) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (sessionManager.getToken().isNotBlank()) {
                refreshProfile()
            }
        }

    }

    suspend fun refreshProfile(): Either<Failure, Unit> {
        try {
            val response = accountApi.refreshProfile()
            val result = response.body()

            when (response.isSuccessful && result != null) {
                true -> {
                    sessionManager.create(
                        Session(
                            id = result.id,
                            name = result.name,
                            email = result.email,
                            token = result.token,
                            role = result.role,
                            gender = result.gender,
                        )
                    )
                    return Either.Right(Unit)
                }

                false -> return Either.Left(response.mapToFailure())
            }


        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga"))

        }
    }

    
}