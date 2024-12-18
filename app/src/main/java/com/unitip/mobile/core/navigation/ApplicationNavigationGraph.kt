package com.unitip.mobile.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.auth.presentation.screens.AuthScreen
import com.unitip.mobile.features.auth.presentation.screens.PickRoleScreen
import com.unitip.mobile.features.chat.screens.ChatScreen
import com.unitip.mobile.features.home.presentation.HomeScreen
import com.unitip.mobile.features.job.screens.CreateJobScreen
import com.unitip.mobile.features.offer.screens.CreateOfferScreen
import com.unitip.mobile.features.test.TestScreen

@Composable
fun ApplicationNavigationGraph(
    navController: NavHostController,
    isAuthenticated: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = when (isAuthenticated) {
            true -> Routes.Home
            false -> Routes.Auth
        },
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    ) {
        // auth
        composable<Routes.Auth> {
            AuthScreen(
                onNavigate = { navController.navigate(it) },
                onDone = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Auth) { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.PickRole> {
            val data: Routes.PickRole = it.toRoute()
            PickRoleScreen(roles = data.roles)
        }

        // home
        composable<Routes.Home> {
            HomeScreen(
                onNavigate = { navController.navigate(it) },
                onLogout = {
                    navController.navigate(Routes.Auth) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                }
            )
        }

        // job
        composable<Routes.CreateJob> {
            CreateJobScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // offer
        composable<Routes.CreateOffer> {
            CreateOfferScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // chat
        composable<Routes.Chat> {
            ChatScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // test
        composable<Routes.Test> {
            TestScreen()
        }
    }
}