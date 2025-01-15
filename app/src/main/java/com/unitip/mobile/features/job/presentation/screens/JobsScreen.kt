package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.User
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.presentation.states.JobsState
import com.unitip.mobile.features.job.presentation.viewmodels.JobsViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.isCustomer
import com.unitip.mobile.shared.presentation.components.CustomCard
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
                verticalAlignment = Alignment.Top,
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
            if (uiState.detail is JobsState.Detail.Loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            if (uiState.detail is JobsState.Detail.Success) {
                AnimatedVisibility(visible = listState.canScrollBackward) {
                    HorizontalDivider()
                }

                LazyColumn(state = listState) {
                    itemsIndexed(uiState.result.jobs) { index, job ->
                        val isExpanded = uiState.expandedJobId == job.id

                        CustomCard(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = if (index == 0) 0.dp else 8.dp
                            ),
                            onClick = {
                                navController.navigate(
                                    JobRoutes.Detail(
                                        id = job.id,
                                        type = job.type
                                    )
                                )
                            }
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.onSurface)
                                    ) {
                                        Icon(
                                            Lucide.User,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(12.dp)
                                                .align(Alignment.Center),
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }
                                    Text(
                                        text = job.customer.name,
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 8.dp,
                                        bottom = 16.dp
                                    )
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = job.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = job.note,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Box(modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable {
                                            viewModel.expandJob(jobId = job.id)
                                        }
                                    ) {
                                        Icon(
                                            if (isExpanded) Lucide.ChevronUp
                                            else Lucide.ChevronDown,
                                            contentDescription = null,
                                            modifier = Modifier.align(
                                                Alignment.Center
                                            )
                                        )
                                    }
                                }

                                AnimatedVisibility(visible = isExpanded) {
                                    Column {
                                        HorizontalDivider()

                                        listOf(
                                            mapOf(
                                                "title" to "Jenis pekerjaan",
                                                "value" to job.type
                                            ),
                                            mapOf(
                                                "title" to "Titik jemput",
                                                "value" to job.pickupLocation
                                            ),
                                            mapOf(
                                                "title" to "Destinasi",
                                                "value" to job.destination
                                            ),
                                        ).mapIndexed { index, item ->
                                            Row(
                                                verticalAlignment = Alignment.Top,
                                                modifier = Modifier.padding(
                                                    start = 16.dp,
                                                    end = 16.dp,
                                                    top = if (index == 0) 16.dp else 4.dp,
                                                )
                                            ) {
                                                Text(
                                                    text = item["title"]!!,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.weight(4f)
                                                )
                                                Text(
                                                    text = item["value"]!!,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    modifier = Modifier.weight(8f)
                                                )
                                            }
                                        }

                                        Row(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                end = 16.dp,
                                                top = 8.dp,
                                                bottom = 16.dp
                                            ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .size(20.dp)
                                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                            ) {
                                                Icon(
                                                    Lucide.User, contentDescription = null,
                                                    modifier = Modifier
                                                        .size(12.dp)
                                                        .align(Alignment.Center)
                                                )
                                            }
                                            Text(
                                                text = "${job.totalApplicants} orang melamar pekerjaan ini",
                                                style = MaterialTheme.typography.labelMedium,
                                                modifier = Modifier
                                                    .padding(start = 8.dp)
                                                    .weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
