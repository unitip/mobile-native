package com.unitip.mobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.auth.screens.AuthScreen
import com.unitip.mobile.features.home.presentation.HomeScreen
import com.unitip.mobile.features.job.screens.CreateJobScreen

@Composable
fun ApplicationNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
    ) {
        composable<Routes.Auth> { AuthScreen() }
        composable<Routes.Home> {
            HomeScreen(
                onNavigate = { navController.navigate(it) }
            )
        }
        composable<Routes.CreateJob> {
            CreateJobScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}