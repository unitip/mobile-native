package com.unitip.mobile.features.auth.utils

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.auth.presentation.screens.AuthScreen
import com.unitip.mobile.features.auth.presentation.screens.PickRoleScreen
import com.unitip.mobile.features.auth.presentation.screens.UnauthorizedScreen

fun NavGraphBuilder.authNavigation() {
    composable<AuthRoutes.Index> { AuthScreen() }
    composable<AuthRoutes.PickRole> {
        val data: AuthRoutes.PickRole = it.toRoute()
        PickRoleScreen(roles = data.roles)
    }
    composable<AuthRoutes.Unauthorized> { UnauthorizedScreen() }
}