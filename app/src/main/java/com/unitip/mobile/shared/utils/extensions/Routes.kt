package com.unitip.mobile.shared.utils.extensions

import androidx.navigation.NavController
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.home.core.HomeRoutes

fun NavController.redirectToUnauthorized() {
    navigate(AuthRoutes.Unauthorized) {
        popUpTo(HomeRoutes.Index) { inclusive = true }
    }
}