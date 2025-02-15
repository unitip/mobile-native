package com.unitip.mobile.features.home.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.presentation.screens.ProfileScreen
import com.unitip.mobile.features.chat.presentation.screens.ChatsScreen
import com.unitip.mobile.features.home.presentation.sceens.DashboardCustomerScreen
import com.unitip.mobile.features.home.presentation.sceens.DashboardDriverScreen
import com.unitip.mobile.features.job.presentation.screens.JobsScreen
import com.unitip.mobile.features.offer.presentation.screens.OffersScreen
import com.unitip.mobile.features.social.presentation.screens.SocialScreen
import com.unitip.mobile.shared.commons.extensions.isDriver
import com.unitip.mobile.shared.domain.models.Session

fun NavGraphBuilder.nestedHomeNavigation(
    session: Session
) {
    composable<HomeRoutes.Dashboard> {
        when (session.isDriver()) {
            true -> DashboardDriverScreen()
            else -> DashboardCustomerScreen()
        }
    }
    composable<HomeRoutes.Jobs> { JobsScreen() }
    composable<HomeRoutes.Offers> { OffersScreen() }
    composable<HomeRoutes.Social> { SocialScreen() }
    composable<HomeRoutes.Chats> { ChatsScreen() }
    composable<HomeRoutes.Profile> { ProfileScreen() }
}