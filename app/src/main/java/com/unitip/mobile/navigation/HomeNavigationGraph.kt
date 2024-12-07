package com.unitip.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.chat.presentation.ChatsScreen
import com.unitip.mobile.features.dashboard.presentation.DashboardScreen
import com.unitip.mobile.features.job.presentation.JobsScreen
import com.unitip.mobile.features.offer.presentation.OffersScreen
import com.unitip.mobile.features.setting.presentation.ProfileScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.Jobs,
    ) {
        composable<Routes.Home.Dashboard> { DashboardScreen() }
        composable<Routes.Home.Jobs> { JobsScreen() }
        composable<Routes.Home.Offers> { OffersScreen() }
        composable<Routes.Home.Chats> { ChatsScreen() }
        composable<Routes.Home.Profile> { ProfileScreen() }
    }
}