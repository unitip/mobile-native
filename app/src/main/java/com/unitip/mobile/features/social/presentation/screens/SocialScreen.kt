package com.unitip.mobile.features.social.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.BadgeHelp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Briefcase
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.unitip.mobile.features.social.presentation.state.SocialState
import com.unitip.mobile.features.social.presentation.viewmodels.SocialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScreen(
    viewModel: SocialViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.getActivities(forceRefresh = false)
    }

    LaunchedEffect(uiState.detail) {
        when (val detail = uiState.detail) {
            is SocialState.Detail.Failure -> {
                snackbarHostState.showSnackbar(message = detail.message)
            }

            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Aktivitas Sosial") },
                    actions = {
                        IconButton(onClick = { viewModel.getActivities(forceRefresh = true) }) {
                            Icon(Lucide.RefreshCw, contentDescription = null)
                        }
                    }
                )

                HorizontalDivider()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val detail = uiState.detail) {
                is SocialState.Detail.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SocialState.Detail.Success -> {
                    LazyColumn(state = listState) {
                        itemsIndexed(detail.activities) { index, activity ->
                            if (index > 0) {
                                HorizontalDivider()
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        when (activity.activityType) {
                                            "job" -> Lucide.Briefcase
                                            "offer" -> Lucide.BadgeHelp
                                            else -> Lucide.Bell
                                        },
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(20.dp)
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${activity.censoredName} membuat ${if (activity.activityType == "job") "Job" else "Offer"} ${activity.timeAgo}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}