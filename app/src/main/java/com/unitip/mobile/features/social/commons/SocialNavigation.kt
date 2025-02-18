package com.unitip.mobile.features.social.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.social.presentation.screens.CreateActivityScreen

fun NavGraphBuilder.socialNavigation() {
    composable<SocialRoutes.Create> {
        CreateActivityScreen()
    }
}