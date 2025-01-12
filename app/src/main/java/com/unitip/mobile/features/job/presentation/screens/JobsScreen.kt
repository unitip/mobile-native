package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCcw
import com.unitip.mobile.features.job.core.JobRoutes
import com.unitip.mobile.features.job.presentation.components.JobListItem
import com.unitip.mobile.features.job.presentation.states.JobsStateDetail
import com.unitip.mobile.features.job.presentation.viewmodels.JobsViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    viewModel: JobsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.detail is JobsStateDetail.Loading

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is JobsStateDetail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.padding(bottom = 0.dp),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Red),
                title = {
                    Text("Jobs")
                },
                actions = {
                    IconButton(onClick = { viewModel.refreshJobs() }) {
                        Icon(
                            Lucide.RefreshCcw,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(JobRoutes.Create) },
            ) {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }
        },
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                // loading indicator
                AnimatedVisibility(visible = isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            if (!isLoading && uiState.detail is JobsStateDetail.Success) {
                itemsIndexed((uiState.detail as JobsStateDetail.Success).result.jobs) { index, job ->
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
            }
        }
    }
}
