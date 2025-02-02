package com.unitip.mobile.features.account.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.account.presentation.screens.EditProfileScreen

fun NavGraphBuilder.settingNavigation() {
    composable<AccountRoutes.Edit> {
        EditProfileScreen()
    }

    composable<AccountRoutes.EditPassword> {
        EditPasswordScreen()
    }

    composable<AccountRoutes.OrderHistory> {
        OrderHistoryScreen()
    }
}