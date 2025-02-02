package com.unitip.mobile.features.home.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.presentation.screens.ProfileScreen
import com.unitip.mobile.features.chat.presentation.screens.ChatsScreen
import com.unitip.mobile.features.home.presentation.sceens.DashboardCustomerScreen
import com.unitip.mobile.features.home.presentation.sceens.DashboardDriverScreen
import com.unitip.mobile.features.job.presentation.screens.JobsScreen
import com.unitip.mobile.features.offer.presentation.screens.OffersScreen

fun NavGraphBuilder.nestedHomeNavigation(
    isDriver: Boolean
) {
    composable<HomeRoutes.Dashboard> {
        when (isDriver) {
            true -> DashboardDriverScreen()
            else -> DashboardCustomerScreen()
        }
    }
//    if (isDriver)
    composable<HomeRoutes.Jobs> { JobsScreen() }
    composable<HomeRoutes.Offers> { OffersScreen() }
    composable<HomeRoutes.Chats> { ChatsScreen() }
    composable<HomeRoutes.Profile> { ProfileScreen() }
}