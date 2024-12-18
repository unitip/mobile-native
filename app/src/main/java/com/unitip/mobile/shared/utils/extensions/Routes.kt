package com.unitip.mobile.shared.utils.extensions

import androidx.navigation.NavHostController
import com.unitip.mobile.shared.presentation.navigation.Routes

fun NavHostController.redirectToUnauthorized() {
    navigate(Routes.Unauthorized) {
        popUpTo(Routes.Home) { inclusive = true }
    }
}