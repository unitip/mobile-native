package com.unitip.mobile.features.home.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.home.domain.models.GetDashboardDriverResult
import com.unitip.mobile.network.openapi.apis.AccountApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDriverRepository @Inject constructor(
    private val accountApi: AccountApi
) {
    suspend fun getDashboard(): Either<Failure, GetDashboardDriverResult> = try {
        val response = accountApi.getDashboardDriver()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                GetDashboardDriverResult(
                    applications = result.applications.map {
                        GetDashboardDriverResult.Application(
                            id = it.id,
                            bidPrice = it.bidPrice,
                            bidNote = it.bidNote,
                            job = GetDashboardDriverResult.Application.Job(
                                id = it.job.id,
                                note = it.job.note,
                                expectedPrice = it.job.expectedPrice,
                                customer = GetDashboardDriverResult.Application.Job.Customer(
                                    name = it.job.customer.name
                                )
                            ),
                        )
                    },
                    jobs = emptyList(),
                    offers = emptyList()
                )
            )

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}