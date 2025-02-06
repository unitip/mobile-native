package com.unitip.mobile.shared.commons.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        // Fallback ke browser jika Google Maps tidak terinstall
        val browserUri = Uri.parse("https://www.google.com/maps?q=$latitude,$longitude")
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}