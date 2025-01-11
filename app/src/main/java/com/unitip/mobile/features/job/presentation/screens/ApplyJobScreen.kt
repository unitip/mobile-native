package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCcw
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import com.unitip.mobile.features.job.presentation.states.DetailJobStateDetail
import com.unitip.mobile.features.job.presentation.viewmodels.ApplyJobViewModel
import com.unitip.mobile.shared.presentation.compositional.LocalNavController

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Apply Job") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Lucide.ArrowLeft,
                            contentDescription = null
                        )
                    }
                },

                )
        })
    {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
//            // Loading indicator
//            AnimatedVisibility(visible = uiState.detail is Detail.Loading) {
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
//                    strokeCap = StrokeCap.Round
//                )
//            }

            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = priceInput,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        priceInput = newValue
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
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
                Text("Apply")
            }
        }
    }
}