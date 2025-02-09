package com.unitip.mobile.shared.commons.extensions

import java.text.NumberFormat
import java.util.Locale

fun Int.toLocalCurrencyFormat(): String {
    val locale = Locale("id", "ID")
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    return currencyFormatter.format(this)
}