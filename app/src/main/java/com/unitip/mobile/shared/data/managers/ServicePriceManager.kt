package com.unitip.mobile.shared.data.managers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unitip.mobile.shared.data.providers.PreferencesProvider
import com.unitip.mobile.shared.domain.models.CategoryPrice
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServicePriceManager @Inject constructor(
    preferencesProvider: PreferencesProvider
) {
    companion object {
        private const val KEY = "com.unitip.mobile.service_prices"
    }

    private val preferences = preferencesProvider.client
    private val gson = Gson()

    fun setPrices(prices: List<CategoryPrice>) =
        preferences.edit()
            .putString(KEY, gson.toJson(prices))
            .apply()

    fun getPrices(): List<CategoryPrice> =
        preferences.getString(KEY, null).let {
            when (it != null) {
                true -> gson.fromJson(it, object : TypeToken<List<CategoryPrice>>() {}.type)
                else -> emptyList()
            }
        }
}