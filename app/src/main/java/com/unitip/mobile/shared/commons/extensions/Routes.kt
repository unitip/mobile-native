package com.unitip.mobile.shared.commons.extensions

import androidx.navigation.NavController
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.home.commons.HomeRoutes

fun NavController.redirectToUnauthorized() {
    navigate(AuthRoutes.Unauthorized) {
        popUpTo(HomeRoutes.Index) { inclusive = true }
    }
}