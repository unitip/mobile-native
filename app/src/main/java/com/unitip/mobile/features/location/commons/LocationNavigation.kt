package com.unitip.mobile.features.location.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.location.presentation.screens.PickLocationScreen

fun NavGraphBuilder.locationNavigation() {
    composable<LocationRoutes.PickLocation> { PickLocationScreen() }
}