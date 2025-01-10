package com.unitip.mobile.features.example.utils

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.example.presentation.screens.ExampleUsersScreen

fun NavGraphBuilder.exampleNavigation() {
    composable<ExampleRoutes.Users> { ExampleUsersScreen() }
}