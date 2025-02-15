package com.unitip.mobile.features.home.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.home.domain.models.GetDashboardDriverResult
import com.unitip.mobile.network.openapi.apis.AccountApi
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriverDashboardRepository @Inject constructor(
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
                    jobs = result.jobs.map {
                        GetDashboardDriverResult.Job(
                            id = it.id,
                            customer = GetDashboardDriverResult.Job.Customer(
                                name = it.customer.name
                            )
                        )
                    },
                    offers = result.offers.map {
                        GetDashboardDriverResult.Offer(
                            id = it.id,
                            title = it.title,
                            price = it.price,
                            description = it.description,
                            pickupArea = it.pickupArea,
                            destinationArea = it.destinationArea,
                            type = it.type,
                            availableUntil = it.availableUntil,
                            maxParticipants = it.maxParticipants,
                            applicants = it.applicants.map { applicant ->
                                GetDashboardDriverResult.Offer.Applicant(
                                    id = applicant.id,
                                    customerName = applicant.customerName,
                                    pickupLocation = applicant.pickupLocation,
                                    destinationLocation = applicant.destinationLocation,
                                    status = applicant.status,
                                    finalPrice = applicant.finalPrice
                                )
                            }
                        )
                    }
                )
            )
            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}