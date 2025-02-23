package com.unitip.mobile.shared.commons

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.commons.settingNavigation
import com.unitip.mobile.features.activity.commons.socialNavigation
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.commons.authNavigation
import com.unitip.mobile.features.chat.commons.chatNavigation
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.features.home.commons.homeNavigation
import com.unitip.mobile.features.job.commons.jobNavigation
import com.unitip.mobile.features.location.commons.locationNavigation
import com.unitip.mobile.features.offer.commons.offerNavigation
import com.unitip.mobile.features.test.TestScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicationNavigationGraph(
    navController: NavHostController,
    isAuthenticated: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = when (isAuthenticated) {
            true -> HomeRoutes.Index
//            true -> JobRoutes.Detail(
//                jobId = "rec_cucg585c8vb430m9jr00"
//            )

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
        locationNavigation()
        settingNavigation()
        socialNavigation()

        // test
        composable<Routes.Test> {
            TestScreen()
        }
    }
}