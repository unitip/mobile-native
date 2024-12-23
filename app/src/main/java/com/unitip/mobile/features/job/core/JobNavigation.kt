package com.unitip.mobile.features.job.core

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.presentation.screens.CreateJobScreen
import com.unitip.mobile.features.job.presentation.screens.DetailJobScreen

fun NavGraphBuilder.jobNavigation() {
    composable<JobRoutes.Create> {
        CreateJobScreen()
    }

    composable<JobRoutes.Detail> {
        val data = it.toRoute<JobRoutes.Detail>()
        DetailJobScreen(id = data.id)
    }
}