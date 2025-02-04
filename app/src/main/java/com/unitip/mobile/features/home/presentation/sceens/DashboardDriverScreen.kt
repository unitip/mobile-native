package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.home.presentation.states.DashboardDriverState
import com.unitip.mobile.features.home.presentation.viewmodels.DashboardDriverViewModel
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@Composable
fun DashboardDriverScreen(
    viewModel: DashboardDriverViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "ini dashboard untuk driver")
            Text(text = "Berikut beberapa order yang Anda ambil")

            Button(
                onClick = {
                    viewModel.getAllOrders()
                }
            ) {
                Text(text = "Refresh")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                with(uiState.detail) {
                    when (this) {
                        is DashboardDriverState.Detail.Loading -> item {
                            CircularProgressIndicator()
                        }

                        is DashboardDriverState.Detail.Success ->
                            when (orders.isNotEmpty()) {
                                true -> items(orders) { order ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            navController.navigate(
                                                JobRoutes.DetailOrderDriver(
                                                    orderId = order.id
                                                )
                                            )
                                        },
                                        overlineContent = { Text(text = order.id) },
                                        headlineContent = { Text(text = order.title) },
                                        supportingContent = { Text(text = order.note) }
                                    )
                                }

                                else -> item {
                                    Text(text = "Tidak ada order")
                                }
                            }

                        else -> Unit
                    }
                }
            }
        }
    }
}