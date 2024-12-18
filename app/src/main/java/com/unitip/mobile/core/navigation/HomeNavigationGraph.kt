package com.unitip.mobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.chat.screens.ChatsScreen
import com.unitip.mobile.features.dashboard.screens.DashboardScreen
import com.unitip.mobile.features.job.screens.JobsScreen
import com.unitip.mobile.features.offer.screens.OffersScreen
import com.unitip.mobile.features.setting.presentation.ProfileScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    onNavigate: (route: Any) -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.Profile,
    ) {
        composable<Routes.Home.Dashboard> { DashboardScreen() }
        composable<Routes.Home.Jobs> {
            JobsScreen(
                onNavigate = onNavigate,
            )
        }
        composable<Routes.Home.Offers> {
            OffersScreen(
                onNavigate = onNavigate,
            )
        }
        composable<Routes.Home.Chats> {
            ChatsScreen(
                onNavigate = onNavigate,
            )
        }
        composable<Routes.Home.Profile> {
            ProfileScreen(
                onNavigate = onNavigate,
            )
        }
    }
}