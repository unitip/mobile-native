package com.unitip.mobile.features.social.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.unitip.mobile.features.social.presentation.viewmodels.CreateActivityViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.features.social.presentation.state.CreateActivityState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateActivityScreen(
    viewModel: CreateActivityViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Aktivitas Baru") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Lucide.ArrowLeft, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            CustomTextField(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                label = "Deskripsikan Aktivitas Anda",
                value = content,
                onValueChange = { value -> content = value },
                minLines = 5
            )
            Button(
                enabled = uiState.detail is State.Detail.Loading,
                onClick = {
                    viewModel.upload(
                        content = content
                    )
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Box {
                    Text(
                        text = "Unggah",
                        modifier = Modifier.alpha(
                            if (uiState.detail is State.Detail.Loading) 0f
                            else 1f
                        )
                    )
                    if (uiState.detail is State.Detail.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(ButtonDefaults.IconSize),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp
                        )
                }
            }
        }
    }
}