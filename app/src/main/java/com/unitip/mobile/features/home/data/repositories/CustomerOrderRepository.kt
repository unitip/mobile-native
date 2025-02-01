package com.unitip.mobile.features.home.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.home.data.sources.CustomerOrderApi
import com.unitip.mobile.features.home.domain.models.Order
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerOrderRepository @Inject constructor(
    sessionManager: SessionManager,
    private val customerOrderApi: CustomerOrderApi
) {
    private val session = sessionManager.read()

    suspend fun getAll(): Either<Failure, List<Order>> = try {
        val response = customerOrderApi.getAll(
            token = "Bearer ${session.token}"
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                result.orders.map {
                    Order(
                        id = it.id,
                        title = it.title,
                        note = it.note
                    )
                }
            )

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}