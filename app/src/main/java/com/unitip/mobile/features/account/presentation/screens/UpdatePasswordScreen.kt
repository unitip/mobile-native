package com.unitip.mobile.features.account.presentation.screens

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.account.presentation.viewmodels.UpdatePasswordViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.features.account.presentation.states.UpdatePasswordState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreen(
    viewModel: UpdatePasswordViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val keyboard = LocalSoftwareKeyboardController.current

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Success -> navController.popBackStack()
                is State.Detail.Failure -> {
                    snackbarHostState.showSnackbar(message = message)
                    viewModel.resetState()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ubah Kata Sandi") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
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
                .fillMaxSize()
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.surfaceContainerHigh,
//                            MaterialTheme.colorScheme.surfaceContainerLowest,
//                        )
//                    )
//                )
                .padding(it)
        ) {
            CustomTextField(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                label = "Kata Sandi Baru",
                value = newPassword,
                onValueChange = { newPassword = it },
                enabled = uiState.detail !is State.Detail.Loading
            )
            CustomTextField(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                label = "Konfirmasi Kata Sandi Baru",
                value = confirmNewPassword,
                onValueChange = { confirmNewPassword = it },
                enabled = uiState.detail !is State.Detail.Loading
            )
            Button(
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End),
                onClick = {
                    keyboard?.hide()
                    viewModel.save(
                        password = newPassword,
                        confirmPassword = confirmNewPassword
                    )
                }
            ) {
                Box {
                    Text(
                        text = "Simpan Perubahan",
                        modifier = Modifier.alpha(
                            when (uiState.detail is State.Detail.Loading) {
                                true -> 0f
                                else -> 1f
                            }
                        )
                    )
                    if (uiState.detail is State.Detail.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(ButtonDefaults.IconSize)
                                .align(Alignment.Center),
                            strokeWidth = 2.dp,
                            strokeCap = StrokeCap.Round
                        )
                }
            }
        }
    }
}