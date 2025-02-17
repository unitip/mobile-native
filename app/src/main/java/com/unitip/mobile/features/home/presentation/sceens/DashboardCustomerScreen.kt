package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Package
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.RefreshCw
import com.unitip.mobile.features.home.presentation.viewmodels.DashboardCustomerViewModel
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.constants.ServiceTypeConstant

private data class OurService(
    val icon: ImageVector,
    val title: String,
    val value: ServiceTypeConstant
)

private val ourServices = listOf(
    OurService(
        icon = Lucide.Bike,
        title = "Antar Jemput",
        value = ServiceTypeConstant.AntarJemput
    ),
    OurService(
        icon = Lucide.Package,
        title = "Jasa Titip",
        value = ServiceTypeConstant.JasaTitip
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCustomerScreen(
    viewModel: DashboardCustomerViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()
    val session = viewModel.session

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ringkasan") },
                    actions = {
                        IconButton(onClick = { viewModel.getAllOrders() }) {
                            Icon(Lucide.RefreshCw, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(JobRoutes.Create)
                }
            ) {
                Icon(Lucide.Plus, contentDescription = null)
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
            sides = WindowInsetsSides.Top
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                Text(
                    text = "Halo, ${session.name}!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    text = "Berikut kami sajikan beberapa ringkasan untuk Anda",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    color = ListItemDefaults.colors().supportingTextColor
                )

                Card(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    colors = CardDefaults.cardColors()
                        .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.onlineDriverIds.size.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Driver Aktif",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Segera pesan jasa driver atau ikuti penawaran yang mereka tawarkan!",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Layanan Kami",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ourServices.map {
                        OutlinedCard(onClick = {}, modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    it.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                                Text(text = it.title)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Pesanan Anda",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Berikut beberapa pesanan Anda yang sedang aktif",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}
