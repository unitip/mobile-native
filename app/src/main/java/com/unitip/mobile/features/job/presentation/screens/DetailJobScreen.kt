package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCcw
import com.unitip.mobile.features.job.core.JobRoutes
import com.unitip.mobile.features.job.presentation.states.DetailJobStateDetail
import com.unitip.mobile.features.job.presentation.viewmodels.DetailJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJobScreen(
    id: String,
    type: String,
    viewModel: DetailJobViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.detail is DetailJobStateDetail.Loading

    LaunchedEffect(id) {
        viewModel.fetchData(jobId = id, type = type)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Job") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Lucide.ArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.fetchData(jobId = id, type = type) }) {
                        Icon(
                            Lucide.RefreshCcw,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            // loading indicator
            AnimatedVisibility(visible = uiState.detail is DetailJobStateDetail.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                    strokeCap = StrokeCap.Round
                )
            }

            if (uiState.detail is DetailJobStateDetail.Success) {
                val result = (uiState.detail as DetailJobStateDetail.Success).result

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                            Text(
                                text = result.job.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = result.job.note,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline)
                            )
                        }
                    }

                    item {
                        HorizontalDivider()
                        Text(
                            text = "Daftar Applicants",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    // list applicants
                    items(result.applicants) { applicant ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 12.dp
                                        )
                                        .weight(1f)
                                ) {
                                    Text(text = applicant.name)
                                    Text(text = "Rp ${applicant.price}")
                                }

                                IconButton(onClick = {}) {
                                    Icon(Lucide.Check, contentDescription = null)
                                }
                            }
                        }
                    }

                    // button apply for driver
                    item {
                        Button(
                            onClick = {
                                navController.navigate(
                                    JobRoutes.Apply(
                                        id = id, type = type
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) { Text(text = "Apply") }

                    }
                }
            }

            // error handling
            if (uiState.detail is DetailJobStateDetail.Failure) {
                val result = uiState.detail as DetailJobStateDetail.Failure

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Terjadi kesalahan!")
                    Text(text = result.message)
                    OutlinedButton(onClick = {
                        viewModel.fetchData(
                            jobId = id,
                            type = type
                        )
                    }) { Text(text = "Refresh") }
                }
            }
        }
    }
}