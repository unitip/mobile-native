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
import com.unitip.mobile.features.auth.core.AuthRoutes
import com.unitip.mobile.features.auth.presentation.states.AuthStateDetail
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.shared.presentation.compositional.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("Rizal Dwi Anggoro") }
    var email by remember { mutableStateOf("customer@unitip.com") }
    var password by remember { mutableStateOf("password") }
    var confirmPassword by remember { mutableStateOf("password") }

    val uiState by viewModel.uiState.collectAsState()
    val isLogin = uiState.isLogin
    val isLoading = uiState.detail is AuthStateDetail.Loading

    val snackbarHost = remember { SnackbarHostState() }
    val navController = LocalNavController.current

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is AuthStateDetail.Success -> {
                    navController.navigate(HomeRoutes.Index) {
                        popUpTo(AuthRoutes.Index) { inclusive = true }
                    }
                    viewModel.resetState()
                }

                is AuthStateDetail.SuccessWithPickRole -> {
                    navController.navigate(AuthRoutes.PickRole(roles = roles))
                    viewModel.resetState()
                }

                is AuthStateDetail.Failure -> {
                    snackbarHost.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (isLogin) {
                            true -> "Masuk"
                            false -> "Registrasi"
                        }
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
            AnimatedVisibility(visible = isLoading) {
                LinearProgressIndicator(
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = when (isLogin) {
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
                if (!uiState.isLogin) OutlinedTextField(
                    enabled = !isLoading,
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    placeholder = {
                        Text(stringResource(R.string.name_placeholder))
                    }
                )
                OutlinedTextField(
                    enabled = !isLoading,
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = (if (uiState.isLogin) 32 else 8).dp),
                    placeholder = {
                        Text(stringResource(R.string.email_placeholder))
                    }
                )
                OutlinedTextField(
                    enabled = !isLoading,
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = {
                        Text(stringResource(R.string.password_placeholder))
                    }
                )
                if (!uiState.isLogin) OutlinedTextField(
                    enabled = !isLoading,
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = {
                        Text(stringResource(R.string.confirm_password_placeholder))
                    }
                )
            }

            // actions
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Button(
                    enabled = !isLoading,
                    onClick = {
                        if (uiState.detail !is AuthStateDetail.Loading) {
                            when (isLogin) {
                                true -> viewModel.login(email = email, password = password)
                                false -> viewModel.register()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(
                            if (isLogin) R.string.login
                            else R.string.register
                        )
                    )
                }
                TextButton(
                    enabled = !isLoading,
                    onClick = { viewModel.switchAuthMode() },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(
                            if (isLogin) R.string.register_switch
                            else R.string.login_switch
                        )
                    )
                }
            }
        }
    }
}