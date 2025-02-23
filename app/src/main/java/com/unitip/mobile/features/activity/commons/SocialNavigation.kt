package com.unitip.mobile.features.activity.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.activity.presentation.screens.CreateActivityScreen

fun NavGraphBuilder.socialNavigation() {
    composable<SocialRoutes.Create> {
        CreateActivityScreen()
    }
}