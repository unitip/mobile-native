package com.unitip.mobile.shared.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.auth.core.AuthRoutes
import com.unitip.mobile.features.auth.core.authNavigation
import com.unitip.mobile.features.chat.core.chatNavigation
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.features.home.core.homeNavigation
import com.unitip.mobile.features.job.core.jobNavigation
import com.unitip.mobile.features.offer.core.offerNavigation
import com.unitip.mobile.features.test.TestScreen

@Composable
fun ApplicationNavigationGraph(
    navController: NavHostController,
    isAuthenticated: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = when (isAuthenticated) {
//            true -> Routes.PickRole(roles = listOf("customer", "driver"))
//            true -> JobRoutes.Detail(id = "rec_ctiekadc8vb720m9371g", type = "antar-jemput")
            true -> HomeRoutes.Index
            false -> AuthRoutes.Index
        },
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    ) {
        authNavigation()
        homeNavigation()
        jobNavigation()
        offerNavigation()
        chatNavigation()

        // test
        composable<Routes.Test> {
            TestScreen()
        }
    }
}