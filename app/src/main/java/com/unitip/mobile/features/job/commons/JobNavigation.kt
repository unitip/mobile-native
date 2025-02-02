package com.unitip.mobile.features.job.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.presentation.screens.CreateJobScreen
import com.unitip.mobile.features.job.presentation.screens.DetailJobScreen
import com.unitip.mobile.features.job.presentation.screens.DetailOrderJobScreen

fun NavGraphBuilder.jobNavigation() {
    composable<JobRoutes.Create> {
        CreateJobScreen()
    }

    composable<JobRoutes.Detail> {
        val data = it.toRoute<JobRoutes.Detail>()
        DetailJobScreen(jobId = data.jobId)
    }

    composable<JobRoutes.DetailOrder> {
        val data = it.toRoute<JobRoutes.DetailOrder>()
        DetailOrderJobScreen(
            orderId = data.orderId
        )
    }
}