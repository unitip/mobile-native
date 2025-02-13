package com.unitip.mobile.features.account.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.account.data.dtos.GetCustomerOrderHistoriesResponse
import com.unitip.mobile.features.account.data.dtos.GetDriverOrderHistoriesResponse
import com.unitip.mobile.features.account.data.sources.AccountApi
import com.unitip.mobile.features.account.domain.models.Order
import com.unitip.mobile.network.openapi.models.ChangeRoleRequest
import com.unitip.mobile.network.openapi.models.UpdatePasswordRequest
import com.unitip.mobile.network.openapi.models.UpdateProfileRequest
import com.unitip.mobile.shared.commons.configs.ApiConfig
import com.unitip.mobile.shared.commons.constants.GenderConstant
import com.unitip.mobile.shared.commons.extensions.isDriver
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import com.unitip.mobile.shared.domain.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountApi: AccountApi,
    private val accountApi2: com.unitip.mobile.network.openapi.apis.AccountApi,
    private val sessionManager: SessionManager
) {
    companion object {
        private val TAG = AccountRepository::class.java.simpleName
    }

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

    suspend fun getAllRoles(): Either<Failure, List<String>> = try {
        val response = accountApi2.getAllRoles()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.roles)
            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga"))
    }

    suspend fun updateProfile(
        name: String,
        gender: GenderConstant,
    ): Either<Failure, Boolean> = try {
        val response = accountApi2.updateProfile(
            UpdateProfileRequest(
                name = name,
                gender = UpdateProfileRequest.Gender.valueOf(gender.name),
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                /**
                 * simpan perubahan nama dan gender ke session saat ini
                 */
                val currentSession = sessionManager.read()
                sessionManager.create(
                    Session(
                        id = result.id,
                        name = result.name,
                        email = currentSession.email,
                        token = currentSession.token,
                        role = currentSession.role,
                        gender = GenderConstant.valueOf(result.gender.name)
                    )
                )

                Either.Right(true)
            }

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun updatePassword(password: String): Either<Failure, Boolean> = try {
        val response = accountApi2.updatePassword(
            UpdatePasswordRequest(password = password)
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(true)
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun changeRole(
        role: String
    ): Either<Failure, Boolean> = try {
        val response = accountApi2.changeRole(ChangeRoleRequest(role = role))
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                /**
                 * simpan role dan token baru dari database ke session saat ini
                 */
                val currentSession = sessionManager.read()
                sessionManager.create(
                    Session(
                        id = result.id,
                        name = currentSession.name,
                        email = currentSession.email,
                        token = result.token,
                        role = result.role,
                        gender = currentSession.gender
                    )
                )

                /**
                 * karena token berubah maka perlu dilakukan refresh token cache aplikasi
                 * agar user tetap bisa melakukan request dan tidak 401
                 */
                ApiConfig.refreshToken(token = result.token)

                Either.Right(true)
            }

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}