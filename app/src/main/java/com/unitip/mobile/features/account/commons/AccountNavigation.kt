package com.unitip.mobile.features.account.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.presentation.screens.ChangeRoleScreen
import com.unitip.mobile.features.account.presentation.screens.OrderHistoryScreen
import com.unitip.mobile.features.account.presentation.screens.UpdatePasswordScreen
import com.unitip.mobile.features.account.presentation.screens.UpdateProfileScreen

fun NavGraphBuilder.settingNavigation() {
    composable<AccountRoutes.UpdateProfile> {
        UpdateProfileScreen()
    }

    composable<AccountRoutes.UpdatePassword> {
        UpdatePasswordScreen()
    }

    composable<AccountRoutes.OrderHistory> {
        OrderHistoryScreen()
    }

    composable<AccountRoutes.ChangeRole> {
        ChangeRoleScreen()
    }
}