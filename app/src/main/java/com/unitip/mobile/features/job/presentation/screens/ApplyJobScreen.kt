package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import com.unitip.mobile.features.job.presentation.viewmodels.ApplyJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyJobScreen(
    id: String,
    type: String,
    viewModel: ApplyJobViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()
    var priceInput by remember { mutableStateOf("0") }

    LaunchedEffect(uiState.detail) {
        if (uiState.detail is ApplyJobState.Detail.Success) {
            navController.popBackStack()
        }
    }

    Scaffold {
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
            CustomIconButton(
                onClick = { navController.popBackStack() },
                icon = Lucide.ChevronLeft,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(
                    text = "Lamar Pekerjaan",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Silakan ajukan harga yang Anda tawarkan untuk pekerjaan ini sesuai dengan layanan yang akan Anda berikan",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // loading indicator
            AnimatedVisibility(visible = uiState.detail is ApplyJobState.Detail.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    strokeCap = StrokeCap.Round
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = priceInput,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            priceInput = newValue
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.detail !is ApplyJobState.Detail.Loading
                )

                TextField(
                    value = "",
                    onValueChange = { newValue ->
                    },
                    minLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = { Text(text = "Catatan") },
                    enabled = uiState.detail !is ApplyJobState.Detail.Loading
                )
            }

            // button apply
            AnimatedVisibility(visible = uiState.detail !is ApplyJobState.Detail.Loading) {
                Button(
                    onClick = {
                        viewModel.applyJob(
                            jobId = id,
                            type = type,
                            price = priceInput.toInt()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Kirim lamaran")
                }
            }
        }
    }
}