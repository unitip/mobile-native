package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.presentation.states.AuthState
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("Rizal Dwi Anggoro") }
    var email by remember { mutableStateOf("rizaldwianggoro@unitip.com") }
    var password by remember { mutableStateOf("password") }
    var confirmPassword by remember { mutableStateOf("password") }

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHost = remember { SnackbarHostState() }
    val navController = LocalNavController.current

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is AuthState.Detail.Success -> {
                    navController.navigate(HomeRoutes.Index) {
                        popUpTo(AuthRoutes.Index) { inclusive = true }
                    }
                    viewModel.resetState()
                }

                is AuthState.Detail.SuccessWithPickRole -> {
                    navController.navigate(
                        AuthRoutes.PickRole(
                            email = email,
                            password = password,
                            roles = roles
                        )
                    )
                    viewModel.resetState()
                }

                is AuthState.Detail.Failure -> {
                    snackbarHost.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) {
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (uiState.isLogin) "Masuk" else "Registrasi",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = if (uiState.isLogin) "Selamat datang kembali di aplikasi Unitip! Silahkan masukkan beberapa informasi berikut untuk masuk ke akun Unitip Anda"
                    else "Masukkan beberapa informasi berikut untuk bergabung dan menjadi bagian dari keluarga Unitip!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AnimatedVisibility(visible = uiState.detail is AuthState.Detail.Loading) {
                LinearProgressIndicator(
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
            }

            // form
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                AnimatedVisibility(visible = !uiState.isLogin) {
                    OutlinedTextField(
                        enabled = uiState.detail !is AuthState.Detail.Loading,
                        value = name,
                        onValueChange = { value -> name = value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        placeholder = {
                            Text(stringResource(R.string.name_placeholder))
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
                        ),
                    )
                }
                OutlinedTextField(
                    enabled = uiState.detail !is AuthState.Detail.Loading,
                    value = email,
                    onValueChange = { value -> email = value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = (if (uiState.isLogin) 32 else 8).dp),
                    placeholder = {
                        Text(stringResource(R.string.email_placeholder))
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
                    ),
                )
                OutlinedTextField(
                    enabled = uiState.detail !is AuthState.Detail.Loading,
                    value = password,
                    onValueChange = { value -> password = value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = {
                        Text(stringResource(R.string.password_placeholder))
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
                    ),
                )
                AnimatedVisibility(visible = !uiState.isLogin) {
                    OutlinedTextField(
                        enabled = uiState.detail !is AuthState.Detail.Loading,
                        value = confirmPassword,
                        onValueChange = { value -> confirmPassword = value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        placeholder = {
                            Text(stringResource(R.string.confirm_password_placeholder))
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .16f)
                        ),
                    )
                }
            }

            // actions
            AnimatedVisibility(visible = uiState.detail !is AuthState.Detail.Loading) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                ) {
                    Button(
                        onClick = {
                            when (uiState.isLogin) {
                                true -> viewModel.login(email = email, password = password)
                                false -> viewModel.register(
                                    name = name,
                                    email = email,
                                    password = password
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                if (uiState.isLogin) R.string.login
                                else R.string.register
                            )
                        )
                    }
                    TextButton(
                        onClick = { viewModel.switchAuthMode() },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                if (uiState.isLogin) R.string.register_switch
                                else R.string.login_switch
                            )
                        )
                    }
                }
            }
        }
    }
}