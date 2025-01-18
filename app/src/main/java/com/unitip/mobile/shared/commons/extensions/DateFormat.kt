package com.unitip.mobile.shared.commons.extensions

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
fun String.localTimeFormat(): String {
    if (this.isNotBlank()) {
        val utcDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
            .atZone(ZoneId.of("UTC"))
        val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    return "00.00"
}