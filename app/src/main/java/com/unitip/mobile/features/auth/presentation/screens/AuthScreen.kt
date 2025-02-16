package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.presentation.states.AuthState
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField

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
    val scrollState = rememberScrollState()
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
                    snackbarHost.showSnackbar(message = message)
                    viewModel.resetState()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        topBar = {
            Column {
                TopAppBar(title = {
                    Text(
                        text = if (uiState.isLogin) "Masuk"
                        else "Registrasi"
                    )
                })
                HorizontalDivider()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = if (uiState.isLogin) "Selamat datang kembali di aplikasi Unitip! Silahkan masukkan beberapa informasi berikut untuk masuk ke akun Unitip Anda"
                else "Masukkan beberapa informasi berikut untuk bergabung dan menjadi bagian dari keluarga Unitip!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            AnimatedVisibility(visible = !uiState.isLogin) {
                CustomTextField(
                    label = "Nama Lengkap",
                    value = name,
                    onValueChange = { value -> name = value },
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
            CustomTextField(
                label = "Alamat Email",
                value = email,
                onValueChange = { value -> email = value },
                modifier = Modifier.padding(
                    top = if (uiState.isLogin) 16.dp else 8.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            )
            CustomTextField(
                label = "Kata Sandi",
                value = password,
                onValueChange = { value -> password = value },
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
            AnimatedVisibility(visible = !uiState.isLogin) {
                CustomTextField(
                    label = "Konfirmasi Kata Sandi",
                    value = confirmPassword,
                    onValueChange = { value -> confirmPassword = value },
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }

            Button(
                enabled = false,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                onClick = {
                    when (uiState.isLogin) {
                        true -> viewModel.login(email = email, password = password)
                        false -> viewModel.register(
                            name = name,
                            email = email,
                            password = password
                        )
                    }
                }
            ) {
                Box() {
                    Text(
                        text = if (uiState.isLogin) "Masuk"
                        else "Daftar",
                        modifier = Modifier.alpha(if (uiState.detail is AuthState.Detail.Loading) 0f else 1f)
                    )

                    if (uiState.detail is AuthState.Detail.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(ButtonDefaults.IconSize)
                                .align(Alignment.Center),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp
                        )
                }
            }

            HorizontalDivider(thickness = .56.dp)

            Text(
                text = when (uiState.isLogin) {
                    true -> "Akun Anda belum terdaftar pada aplikasi Unitip? " +
                            "Silahkan klik tombol di bawah ini untuk mendaftarkan akun Unitip baru"

                    else -> "Akun Anda sudah terdaftar pada aplikasi Unitip? " +
                            "Silahkan klik tombol di bawah ini untuk masuk dengan akun lama Unitip"
                },
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = if (uiState.isLogin) "Daftarkan akun baru"
                else "Masuk dengan akun lama",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .clickable { viewModel.switchAuthMode() }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}