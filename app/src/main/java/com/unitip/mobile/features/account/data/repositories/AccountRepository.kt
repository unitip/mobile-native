package com.unitip.mobile.features.account.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.account.data.dtos.ChangeRolePayload
import com.unitip.mobile.features.account.data.dtos.EditPasswordPayload
import com.unitip.mobile.features.account.data.dtos.EditPayload
import com.unitip.mobile.features.account.data.dtos.GetCustomerOrderHistoriesResponse
import com.unitip.mobile.features.account.data.dtos.GetDriverOrderHistoriesResponse
import com.unitip.mobile.features.account.data.sources.AccountApi
import com.unitip.mobile.features.account.domain.models.Order
import com.unitip.mobile.shared.commons.extensions.isDriver
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
    @Deprecated("use sessionManager.getToken() instead")
    private val session = sessionManager.read()

    suspend fun getOrderHistories(): Either<Failure, List<Order>> =
        try {
            val response = when (sessionManager.read().isDriver()) {
                true -> accountApi.getDriverOrderHistories(
                    token = "Bearer ${sessionManager.getToken()}"
                )

                else -> accountApi.getCustomerOrderHistories(
                    token = "Bearer ${sessionManager.getToken()}"
                )
            }
            val result = response.body()

            when (response.isSuccessful && result != null) {
                true -> when (sessionManager.read().isDriver()) {
                    true -> Either.Right((result as GetDriverOrderHistoriesResponse).orders.map {
                        Order(
                            id = it.id,
                            title = it.title,
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt,
                            otherUser = Order.OtherUser(
                                name = it.customer.name
                            )
                        )
                    })

                    else -> Either.Right((result as GetCustomerOrderHistoriesResponse).orders.map {
                        Order(
                            id = it.id,
                            title = it.title,
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt,
                            otherUser = Order.OtherUser(
                                name = it.driver.name
                            )
                        )
                    })
                }

                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }

    suspend fun getRoles(): Either<Failure, List<String>> = try {
        val response = accountApi.getRole(
            token = "Bearer ${sessionManager.getToken()}"
        )

        val result = response.body()
        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.roles)
            false -> Either.Left(response.mapToFailure())
        }

    } catch (e: Exception) {
        Either.Left(
            Failure(message = "Terjadi kesalahan tak terduga")
        )
    }

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

    suspend fun changeRole(role: String): Either<Failure, Boolean> {
        try {
            val token = session.token
            val response = accountApi.changeRole(
                token = "Bearer $token",
                payload = ChangeRolePayload(role = role)
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