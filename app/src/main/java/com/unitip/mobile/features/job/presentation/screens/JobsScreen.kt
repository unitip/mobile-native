package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.unitip.mobile.features.job.core.JobRoutes
import com.unitip.mobile.features.job.presentation.components.JobListItem
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.features.job.presentation.viewmodels.JobsViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.isCustomer
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@Composable
fun JobsScreen(
    viewModel: JobsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val listState = rememberLazyListState()

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.detail is JobsState.Detail.Loading

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is JobsState.Detail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
            WindowInsetsSides.Top
        ),
        floatingActionButton = {
            if (uiState.session.isCustomer()) {
                FloatingActionButton(
                    onClick = { navController.navigate(JobRoutes.Create) },
                ) {
                    Icon(Icons.TwoTone.Add, contentDescription = null)
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceContainerHigh,
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                        )
                    )
                )
                .padding(it)
        ) {
            // app bar
            Row(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Jobs", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Berikut adalah beberapa pekerjaan yang dapat Anda lamar sebagai driver",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                CustomIconButton(
                    icon = Lucide.RefreshCw,
                    onClick = { viewModel.refreshJobs() }
                )
            }

            // loading indicator
            AnimatedVisibility(visible = isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    strokeCap = StrokeCap.Round
                )
            }

            AnimatedVisibility(visible = listState.canScrollBackward) {
                HorizontalDivider()
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                state = listState
            ) {
                if (!isLoading && uiState.detail is JobsState.Detail.Success) {
                    itemsIndexed((uiState.detail as JobsState.Detail.Success).result.jobs) { index, job ->
                        JobListItem(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = if (index > 0) 8.dp else 0.dp
                                )
                                .clickable {
                                    navController.navigate(
                                        JobRoutes.Detail(
                                            id = job.id,
                                            type = job.type
                                        )
                                    )
                                },
                            customerName = job.customer.name,
                            title = job.title,
                            note = job.note,
                            type = job.type,
                            pickupLocation = job.pickupLocation,
                            destination = job.destination
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
