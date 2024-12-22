package com.unitip.mobile.shared.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.chat.screens.ChatsScreen
import com.unitip.mobile.features.dashboard.screens.DashboardScreen
import com.unitip.mobile.features.job.presentation.screens.JobsScreen
import com.unitip.mobile.features.offer.screens.OffersScreen
import com.unitip.mobile.features.setting.presentation.screens.ProfileScreen

@Composable
fun HomeNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigate: (route: Any) -> Unit = {},
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Home.Jobs,
    ) {
        composable<Routes.Home.Dashboard> { DashboardScreen() }
        composable<Routes.Home.Jobs> {
            JobsScreen()
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
            ProfileScreen()
        }
    }
}