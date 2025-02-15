package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.MapPinCheck
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.User
import com.unitip.mobile.features.job.commons.JobConstant
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.presentation.components.ApplyJobBottomSheet
import com.unitip.mobile.features.job.presentation.components.JobsLoadingPlaceholder
import com.unitip.mobile.features.job.presentation.viewmodels.JobsViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.localDateFormat
import com.unitip.mobile.shared.commons.extensions.toLocalCurrencyFormat
import com.unitip.mobile.features.job.presentation.states.JobsState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    viewModel: JobsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    val listState = rememberLazyListState()
    var selectedJobId by remember { mutableStateOf("") }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Jobs") },
                    actions = {
                        IconButton(onClick = { viewModel.refreshJobs() }) {
                            Icon(Lucide.RefreshCw, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            with(uiState.detail) {
                when (this) {
                    is State.Detail.Loading -> JobsLoadingPlaceholder()

                    is State.Detail.Success -> LazyColumn(state = listState) {
                        itemsIndexed(jobs) { index, job ->
                            if (index > 0) HorizontalDivider()
                            Box(
                                modifier = Modifier.clickable {
                                    selectedJobId = job.id
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    if (index == 2)
                                        CircularProgressIndicator(
                                            strokeCap = StrokeCap.Round,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    else
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(24.dp))
                                                .background(MaterialTheme.colorScheme.primaryContainer)
                                                .size(40.dp)
                                        ) {
                                            Icon(
                                                Lucide.User,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier
                                                    .align(Alignment.Center)
                                                    .size(20.dp)
                                            )
                                        }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = job.customer.name,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = job.createdAt.localDateFormat(),
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                        }
                                        Text(
                                            text = job.note,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )

                                        listOf(
                                            mapOf(
                                                "icon" to Lucide.Bike,
                                                "value" to when (job.service) {
                                                    JobConstant.Service.AntarJemput -> "Antar jemput"
                                                    JobConstant.Service.JasaTitip -> "Jasa titip"
                                                }
                                            ),
                                            mapOf(
                                                "icon" to Lucide.DollarSign,
                                                "value" to 12000.toLocalCurrencyFormat()
                                            ),
                                            mapOf(
                                                "icon" to Lucide.MapPin,
                                                "value" to job.pickupLocation
                                            ),
                                            mapOf(
                                                "icon" to Lucide.MapPinCheck,
                                                "value" to job.destinationLocation
                                            )
                                        ).mapIndexed { index, it ->
                                            Row(
                                                modifier = Modifier.padding(
                                                    top = if (index == 0) 8.dp else 4.dp
                                                ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(
                                                    it["icon"]!! as ImageVector,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Text(
                                                    text = it["value"]!! as String,
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }
                                    }
                                }
                            }

//                            CustomCard(
//                                modifier = Modifier.padding(
//                                    start = 16.dp,
//                                    end = 16.dp,
//                                    top = if (index == 0) 0.dp else 8.dp
//                                ),
//                                onClick = {
//                                    navController.navigate(JobRoutes.Detail(jobId = job.id))
//                                }
//                            ) {
//                                Column {
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier.padding(
//                                            start = 16.dp,
//                                            end = 16.dp,
//                                            top = 16.dp
//                                        )
//                                    ) {
//                                        Box(
//                                            modifier = Modifier
//                                                .size(20.dp)
//                                                .clip(RoundedCornerShape(10.dp))
//                                                .background(MaterialTheme.colorScheme.onSurface)
//                                        ) {
//                                            Icon(
//                                                Lucide.User,
//                                                contentDescription = null,
//                                                modifier = Modifier
//                                                    .size(12.dp)
//                                                    .align(Alignment.Center),
//                                                tint = MaterialTheme.colorScheme.surface
//                                            )
//                                        }
//                                        Text(
//                                            text = job.customer.name,
//                                            style = MaterialTheme.typography.labelMedium,
//                                            modifier = Modifier.padding(start = 8.dp)
//                                        )
//                                    }
//                                    Row(
//                                        verticalAlignment = Alignment.Top,
//                                        modifier = Modifier.padding(
//                                            start = 16.dp,
//                                            end = 16.dp,
//                                            top = 8.dp,
//                                            bottom = 16.dp
//                                        )
//                                    ) {
//                                        Column(modifier = Modifier.weight(1f)) {
//                                            Text(
//                                                text = job.title,
//                                                style = MaterialTheme.typography.titleMedium
//                                            )
//                                            Text(
//                                                text = job.note,
//                                                style = MaterialTheme.typography.bodySmall
//                                            )
//                                        }
//                                        Box(modifier = Modifier
//                                            .padding(start = 8.dp)
//                                            .size(40.dp)
//                                            .clip(RoundedCornerShape(20.dp))
//                                            .clickable {
//                                                viewModel.expandJob(jobId = job.id)
//                                            }
//                                        ) {
//                                            Icon(
//                                                if (isExpanded) Lucide.ChevronUp
//                                                else Lucide.ChevronDown,
//                                                contentDescription = null,
//                                                modifier = Modifier.align(
//                                                    Alignment.Center
//                                                )
//                                            )
//                                        }
//                                    }
//
//                                    AnimatedVisibility(visible = isExpanded) {
//                                        Column {
//                                            HorizontalDivider()
//
//                                            listOf(
//                                                mapOf(
//                                                    "title" to "Jenis pekerjaan",
//                                                    "value" to job.service
//                                                ),
//                                                mapOf(
//                                                    "title" to "Titik jemput",
//                                                    "value" to job.pickupLocation
//                                                ),
//                                                mapOf(
//                                                    "title" to "Destinasi",
//                                                    "value" to job.destinationLocation
//                                                ),
//                                            ).mapIndexed { index, item ->
//                                                Row(
//                                                    verticalAlignment = Alignment.Top,
//                                                    modifier = Modifier.padding(
//                                                        start = 16.dp,
//                                                        end = 16.dp,
//                                                        top = if (index == 0) 16.dp else 4.dp,
//                                                        bottom = if (index == 2) 16.dp else 0.dp
//                                                    )
//                                                ) {
//                                                    Text(
//                                                        text = item["title"]!!,
//                                                        style = MaterialTheme.typography.bodySmall,
//                                                        fontWeight = FontWeight.SemiBold,
//                                                        modifier = Modifier.weight(4f)
//                                                    )
//                                                    Text(
//                                                        text = item["value"]!!,
//                                                        style = MaterialTheme.typography.bodySmall,
//                                                        modifier = Modifier.weight(8f)
//                                                    )
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    /**
     * bottom sheet untuk apply job bagi role driver
     */
    if (selectedJobId.isNotBlank())
        ApplyJobBottomSheet(
            onSend = { withOffer ->
                when (withOffer) {
                    true -> navController.navigate(JobRoutes.Apply(jobId = selectedJobId))
                    else -> Unit
                }
                selectedJobId = ""
            },
            onDismiss = { selectedJobId = "" }
        )
}
