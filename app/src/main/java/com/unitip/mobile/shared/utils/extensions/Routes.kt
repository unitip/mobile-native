package com.unitip.mobile.shared.utils.extensions

import androidx.navigation.NavController
import com.unitip.mobile.shared.presentation.navigation.Routes

fun NavController.redirectToUnauthorized() {
    navigate(Routes.Unauthorized) {
        popUpTo(Routes.Home) { inclusive = true }
    }
}