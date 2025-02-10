    package com.unitip.mobile.features.home.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.home.presentation.sceens.HomeScreen

fun NavGraphBuilder.homeNavigation() {
    composable<HomeRoutes.Index> { HomeScreen() }
}