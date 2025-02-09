package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.job.commons.JobConstant
import com.unitip.mobile.features.job.presentation.viewmodels.DetailOrderJobCustomerViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.features.job.presentation.states.DetailOrderJobCustomerState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailOrderJobCustomerScreen(
    viewModel: DetailOrderJobCustomerViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Order") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Lucide.ArrowLeft, contentDescription = null)
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
            with(uiState.detail) {
                when (this) {
                    is State.Detail.Loading -> CircularProgressIndicator()
                    is State.Detail.Success -> LazyColumn {
                        item {
                            Text(text = data.id)
                            Text(text = data.title)
                            Text(text = data.note)
                            Text(text = "price: ${data.price}")
                            Text(text = "status: ${data.status}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "daftar applications")
                        }

                        /**
                         * kondisi dimana customer sudah mendapatkan/memilih driver
                         */
                        if ((data.status == JobConstant.Status.Ongoing || data.status == JobConstant.Status.Done) && data.driver != null) {
                            item {
                                Text(text = "berikut driver anda")
                                Text(text = "driver id: ${data.driver.id}")
                                Text(text = "driver name: ${data.driver.name}")
                            }
                        }

                        /**
                         * kondisi dimana customer belum mendapatkan/memilih driver
                         * sehingga akan ditampilkan daftar lamaran dari beberapa
                         * driver
                         */
                        if (data.status == JobConstant.Status.Initial) {
                            items(data.applications) {
                                ListItem(
                                    headlineContent = {
                                        Text(text = it.driver.name)
                                    },
                                    supportingContent = {
                                        Text(text = it.bidNote)
                                    },
                                    overlineContent = {
                                        Text(text = "Rp ${it.bidPrice}")
                                    },
                                    trailingContent = {
                                        IconButton(onClick = {}) {
                                            Icon(Lucide.Check, contentDescription = null)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}