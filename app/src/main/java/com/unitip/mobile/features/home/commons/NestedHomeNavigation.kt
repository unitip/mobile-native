package com.unitip.mobile.features.home.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.chat.presentation.screens.ChatsScreen
import com.unitip.mobile.features.home.presentation.sceens.DashboardScreen
import com.unitip.mobile.features.job.presentation.screens.JobsScreen
import com.unitip.mobile.features.offer.presentation.screens.OffersScreen
import com.unitip.mobile.features.setting.presentation.screens.ProfileScreen

fun NavGraphBuilder.nestedHomeNavigation() {
    composable<HomeRoutes.Dashboard> { DashboardScreen() }
    composable<HomeRoutes.Jobs> { JobsScreen() }
    composable<HomeRoutes.Offers> { OffersScreen() }
    composable<HomeRoutes.Chats> { ChatsScreen() }
    composable<HomeRoutes.Profile> { ProfileScreen() }
}