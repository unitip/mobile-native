package com.unitip.mobile.shared.data.repositories

import android.util.Log
import arrow.core.Either
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.ServicePriceManager
import com.unitip.mobile.shared.data.sources.ServiceApi
import com.unitip.mobile.shared.domain.models.CategoryPrice
import com.unitip.mobile.shared.domain.models.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
    private val serviceApi: ServiceApi,
    private val servicePriceManager: ServicePriceManager
) {
    companion object {
        private const val TAG = "ServiceRepository"
    }

    init {
        Log.d(TAG, "init called")
        CoroutineScope(Dispatchers.IO).launch {
            getAllPrices()
        }
    }

    suspend fun getAllPrices(): Either<Failure, List<CategoryPrice>> = try {
        val response = serviceApi.getAllPrices()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> {
                val prices = result.categories.map { category ->
                    CategoryPrice(
                        title = category.title,
                        prices = category.prices.map { price ->
                            CategoryPrice.Price(
                                title = price.title,
                                minPrice = price.minPrice,
                                maxPrice = price.maxPrice,
                            )
                        },
                    )
                }

                /**
                 * simpan harga ke local agar dapat diakses dengan cepat tanpa perlu
                 * melakukan request ulang ke server
                 */
                servicePriceManager.setPrices(prices)

                Either.Right(prices)
            }

            else -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}