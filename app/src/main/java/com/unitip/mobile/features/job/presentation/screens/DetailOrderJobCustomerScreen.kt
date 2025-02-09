package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.User
import com.unitip.mobile.features.job.commons.JobConstant
import com.unitip.mobile.features.job.presentation.viewmodels.DetailOrderJobCustomerViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.toLocalCurrencyFormat
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
            Column {
                TopAppBar(
                    title = { Text(text = "Rincian Pesanan") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Lucide.ArrowLeft, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            with(uiState.detail) {
                when (this) {
                    is State.Detail.Loading -> Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            strokeCap = StrokeCap.Round
                        )
                    }

                    is State.Detail.Success -> LazyColumn {
                        item {
                            Text(
                                text = data.note,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )

                            ListItem(
                                leadingContent = {
                                    Icon(
                                        Lucide.Bike,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = { Text(text = "Jenis Layanan") },
                                supportingContent = {
                                    Text(
                                        text = when (data.service) {
                                            JobConstant.Service.AntarJemput -> "Antar jemput"
                                            JobConstant.Service.JasaTitip -> "Jasa titip"
                                        }
                                    )
                                }
                            )
                            ListItem(
                                leadingContent = {
                                    Icon(
                                        Lucide.MapPin,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = { Text(text = "Lokasi Penjemputan") },
                                supportingContent = { Text(text = data.pickupLocation) }
                            )
                            ListItem(
                                leadingContent = {
                                    Box(modifier = Modifier.size(24.dp))
                                },
                                headlineContent = { Text(text = "Lokasi Pengantaran") },
                                supportingContent = { Text(text = data.destinationLocation) }
                            )
                            ListItem(
                                leadingContent = {
                                    Icon(
                                        Lucide.DollarSign,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = { Text(text = "Harga") },
                                supportingContent = {
                                    Text(
                                        text =
                                        data.expectedPrice.toLocalCurrencyFormat()
                                            .plus(
                                                when (data.status != JobConstant.Status.Initial && data.expectedPrice != data.price) {
                                                    true -> " -> ${data.price.toLocalCurrencyFormat()}"
                                                    else -> ""
                                                }
                                            )
                                    )
                                }
                            )
                            ListItem(
                                leadingContent = {
                                    Icon(
                                        Lucide.Tag,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = { Text(text = "Status Pesanan") },
                                supportingContent = {
                                    Text(
                                        text = when (data.status) {
                                            JobConstant.Status.Initial -> "Menunggu aplikasi driver"
                                            JobConstant.Status.Ongoing -> "Sedang diproses"
                                            JobConstant.Status.Done -> "Selesai"
                                        }
                                    )
                                }
                            )
                        }

                        /**
                         * kondisi dimana customer sudah mendapatkan/memilih driver
                         */
                        if ((data.status == JobConstant.Status.Ongoing || data.status == JobConstant.Status.Done) && data.driver != null) {
                            item {
                                HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                                Text(
                                    text = "Data Driver",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp
                                    )
                                )
                                ListItem(
                                    leadingContent = {
                                        Icon(
                                            Lucide.User,
                                            contentDescription = null
                                        )
                                    },
                                    headlineContent = { Text(text = "Nama") },
                                    supportingContent = { Text(text = data.driver.name) },
                                    trailingContent = {
                                        IconButton(onClick = {}) {
                                            Icon(Lucide.MessageSquare, contentDescription = null)
                                        }
                                    }
                                )
                            }
                        }

                        /**
                         * kondisi dimana customer belum mendapatkan/memilih driver
                         * sehingga akan ditampilkan daftar lamaran dari beberapa
                         * driver
                         */
                        if (data.status == JobConstant.Status.Initial) {
                            item {
                                Text(text = "daftar applications")
                            }

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