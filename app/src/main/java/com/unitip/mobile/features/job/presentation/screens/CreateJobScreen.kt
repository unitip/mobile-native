package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.job.presentation.viewmodels.CreateJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.constants.ServiceTypeConstant
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.shared.presentation.components.ServiceTypeDropdown
import com.unitip.mobile.features.job.presentation.states.CreateJobState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    viewModel: CreateJobViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    var note by remember { mutableStateOf("") }
    var pickupLocation by remember { mutableStateOf("") }
    var destinationLocation by remember { mutableStateOf("") }

    /**
     * expected price merupakan harga standar yang akan diisi
     * berdasarkan pilihan kategori lokasi user
     */
    var expectedPrice by remember { mutableIntStateOf(0) }
    var selectedService by remember { mutableStateOf<ServiceTypeConstant?>(null) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Failure -> {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                is State.Detail.Success -> navController.popBackStack()

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(Lucide.ArrowLeft, contentDescription = null)
                        }
                    },
                    title = { Text(text = "Pekerjaan Baru") }
                )
                HorizontalDivider()
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ServiceTypeDropdown(value = selectedService) { value ->
                selectedService = value
            }

            CustomTextField(
                label = "Lokasi penjemputan",
                value = pickupLocation,
                onValueChange = { value -> pickupLocation = value },
                placeholder = "Cth: Kos Unitip no.2",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                enabled = uiState.detail !is State.Detail.Loading,
            )

            CustomTextField(
                label = "Lokasi tujuan",
                value = destinationLocation,
                onValueChange = { value -> destinationLocation = value },
                placeholder = "Cth: Gacoan Jebres",
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                enabled = uiState.detail !is State.Detail.Loading
            )

            CustomTextField(
                label = "Expected price",
                value = expectedPrice.toString(),
                onValueChange = { value ->
                    expectedPrice = value.toIntOrNull() ?: 0
                },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            CustomTextField(
                label = "Catatan tambahan",
                value = note,
                onValueChange = { note = it },
                placeholder = "Cth: Tolong bawain helm",
                minLines = 5,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                enabled = uiState.detail !is State.Detail.Loading
            )

            Button(
                enabled = uiState.detail !is State.Detail.Loading,
                onClick = {
                    if (selectedService != null)
                        viewModel.create(
                            note = note,
                            pickupLocation = pickupLocation,
                            destinationLocation = destinationLocation,
                            service = selectedService!!,
                            expectedPrice = expectedPrice
                        )
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
            ) {
                Box {
                    Text(
                        "Selesai",
                        modifier = Modifier.alpha(
                            if (uiState.detail is State.Detail.Loading) 0f
                            else 1f
                        )
                    )
                    if (uiState.detail is State.Detail.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(ButtonDefaults.IconSize)
                                .align(Alignment.Center),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp
                        )
                }
            }
        }
    }
}