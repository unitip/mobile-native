package com.unitip.mobile.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.auth.presentation.screens.AuthScreen
import com.unitip.mobile.features.chat.screens.ChatScreen
import com.unitip.mobile.features.home.presentation.HomeScreen
import com.unitip.mobile.features.job.screens.CreateJobScreen
import com.unitip.mobile.features.offer.screens.CreateOfferScreen

@Composable
fun ApplicationNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    ) {
        // auth
        composable<Routes.Auth> { AuthScreen() }

        // home
        composable<Routes.Home> {
            HomeScreen(
                onNavigate = { navController.navigate(it) }
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
    }
}