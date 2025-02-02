package com.unitip.mobile.features.setting.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.setting.presentation.screens.EditProfileScreen
import com.unitip.mobile.features.setting.presentation.screens.OrderHistoryScreen

fun NavGraphBuilder.settingNavigation() {
    composable<SettingRoutes.Edit> {
        EditProfileScreen()
    }

    composable<SettingRoutes.OrderHistory> {
        OrderHistoryScreen()
    }
}