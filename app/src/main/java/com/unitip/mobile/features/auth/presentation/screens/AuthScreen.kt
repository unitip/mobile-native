package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.presentation.states.AuthState
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.shared.presentation.compositional.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (uiState.isLogin) "Masuk"
                        else "Registrasi"
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = uiState.detail is AuthState.Detail.Loading) {
                LinearProgressIndicator(
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = when (uiState.isLogin) {
                    true -> "Selamat datang kembali di aplikasi Unitip! Silahkan masukkan beberapa informasi berikut untuk masuk ke akun Unitip Anda"
                    false -> "Masukkan beberapa informasi berikut untuk bergabung dan menjadi bagian dari keluarga Unitip!"
                },
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp),
                style = with(MaterialTheme.typography.bodyMedium) {
                    copy(color = MaterialTheme.colorScheme.outline)
                },
                textAlign = TextAlign.Center,
            )

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
                        }
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
                    }
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
                    }
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
                        }
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
                                false -> viewModel.register(name = name, email = email, password = password)
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