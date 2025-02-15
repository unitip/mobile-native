package com.unitip.mobile.features.home.data.repositories

import android.util.Log
import arrow.core.Either
import com.unitip.mobile.features.home.domain.models.OrderDashboard
import com.unitip.mobile.network.openapi.apis.AccountApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject

class CustomerDashboardRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val accountApi: AccountApi
) {

    suspend fun getDashboard(): Either<Failure, List<OrderDashboard>> = try {
        val response = accountApi.getDashboardCustomer()
        Log.d("CustomerDashboardRepository", "getDashboard: ${response.body()}")
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                (result.needAction.orEmpty() + result.ongoing.orEmpty()).map {
                    OrderDashboard(
                        id = it.id ?: "",
                        title = when(it.type) {
                            "job" -> "Job"
                            "offer" -> "Offer driver"
                            else -> "Pesanan"
                        },
                        note = it.note ?: "",
                        type = it.type ?: "",
                        status = it.status
                    )
                }
            )
            else -> {
//                Log.d("CustomerDashboardRepository", "getDashboard: ${response.errorBody()}")
                Either.Left(response.mapToFailure())
            }
        }
    } catch (e: Exception) {
        Log.d("CustomerDashboardRepository", "getDashboard: ${e.message}")
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}