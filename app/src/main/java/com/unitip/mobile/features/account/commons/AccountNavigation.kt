package com.unitip.mobile.features.account.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.presentation.screens.ChangeRoleScreen
import com.unitip.mobile.features.account.presentation.screens.EditPasswordScreen
import com.unitip.mobile.features.account.presentation.screens.EditProfileScreen
import com.unitip.mobile.features.account.presentation.screens.OrderHistoryScreen

fun NavGraphBuilder.settingNavigation() {
    composable<AccountRoutes.UpdateProfile> {
        EditProfileScreen()
    }

    composable<AccountRoutes.UpdatePassword> {
        EditPasswordScreen()
    }

    composable<AccountRoutes.OrderHistory> {
        OrderHistoryScreen()
    }

    composable<AccountRoutes.ChangeRole> {
        ChangeRoleScreen()
    }
}