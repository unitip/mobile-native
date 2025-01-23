package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.unitip.mobile.shared.presentation.components.CustomTextField

@Composable
fun ApplyJobScreen(
    id: String,
    type: String,
    viewModel: ApplyJobViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()
    var price by remember { mutableStateOf("0") }
    var note by remember { mutableStateOf("") }

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

            CustomTextField(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                label = "Harga Ajuan",
                value = price,
                onValueChange = { value ->
                    if (value.all { char -> char.isDigit() })
                        price = value
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CustomTextField(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                label = "Catatan Tambahan",
                value = note,
                onValueChange = { value ->
                    note = value
                },
                minLines = 5
            )

//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
//            ) {
//                Text(
//                    text = "Harga Ajuan",
//                    style = MaterialTheme.typography.labelMedium,
//                )
//                OutlinedTextField(
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    value = priceInput,
//                    onValueChange = { newValue ->
//                        if (newValue.all { it.isDigit() }) {
//                            priceInput = newValue
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp),
//                    enabled = uiState.detail !is ApplyJobState.Detail.Loading,
//                    shape = RoundedCornerShape(12.dp),
//                    colors = OutlinedTextFieldDefaults.colors().copy(
//                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
//                    ),
//                )
//
//                Text(
//                    text = "Catatan Tambahan",
//                    style = MaterialTheme.typography.labelMedium,
//                    modifier = Modifier.padding(top = 16.dp)
//                )
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = { newValue ->
//                    },
//                    minLines = 5,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp),
//                    enabled = uiState.detail !is ApplyJobState.Detail.Loading,
//                    shape = RoundedCornerShape(12.dp),
//                    colors = OutlinedTextFieldDefaults.colors().copy(
//                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
//                    ),
//                )
//            }

            // button apply
            AnimatedVisibility(visible = uiState.detail !is ApplyJobState.Detail.Loading) {
                Button(
                    onClick = {
                        viewModel.apply(
                            jobId = id,
                            type = type,
                            price = price.toInt()
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