package com.unitip.mobile.features.location.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.location.presentation.screens.PickLocationScreen

fun NavGraphBuilder.locationNavigation() {
    composable<LocationRoutes.PickLocation> {
        val data = it.toRoute<LocationRoutes.PickLocation>()
        PickLocationScreen(
            resultKey = data.resultKey
        )
    }
}