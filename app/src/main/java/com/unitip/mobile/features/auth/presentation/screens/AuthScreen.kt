package com.unitip.mobile.features.auth.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R
import com.unitip.mobile.features.auth.presentation.states.AuthStateDetail
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel
import com.unitip.mobile.shared.presentation.navigation.Routes

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate: (route: Any) -> Unit,
    onDone: () -> Unit,
) {
    var name by remember { mutableStateOf("Rizal Dwi Anggoro") }
    var email by remember { mutableStateOf("rizaldwianggoro@unitip.com") }
    var password by remember { mutableStateOf("password") }
    var confirmPassword by remember { mutableStateOf("password") }

    val uiState by viewModel.uiState.collectAsState()
    val isLogin = uiState.isLogin

    val context = LocalContext.current

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is AuthStateDetail.Success -> {
                    onDone()
                    viewModel.resetState()
                }

                is AuthStateDetail.SuccessWithPickRole -> {
                    onNavigate(Routes.PickRole(roles = roles))
                    viewModel.resetState()
                }

                is AuthStateDetail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }

                else -> {}
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = when (isLogin) {
                    true -> "Masuk"
                    false -> "Registrasi"
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = when (isLogin) {
                    true -> "Selamat datang kembali di aplikasi Unitip! Silahkan masukkan beberapa informasi berikut untuk masuk ke akun Unitip Anda"
                    false -> "Masukkan beberapa informasi berikut untuk bergabung dan menjadi bagian dari keluarga Unitip!"
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .alpha(.8f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )

            // form
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                if (!uiState.isLogin) OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 32.dp,
                        ),
                    placeholder = {
                        Text(stringResource(R.string.name_placeholder))
                    }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = (if (uiState.isLogin) 32 else 8).dp,
                        ),
                    placeholder = {
                        Text(stringResource(R.string.email_placeholder))
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                        ),
                    placeholder = {
                        Text(stringResource(R.string.password_placeholder))
                    }
                )
                if (!uiState.isLogin) OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                        ),
                    placeholder = {
                        Text(stringResource(R.string.confirm_password_placeholder))
                    }
                )
            }

            Button(
                enabled = uiState.detail !is AuthStateDetail.Loading,
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
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                if (uiState.detail is AuthStateDetail.Loading)
                    CircularProgressIndicator(
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                    )
                else
                    Text(
                        text = stringResource(
                            if (isLogin) R.string.login
                            else R.string.register
                        )
                    )
            }
            TextButton(
                enabled = uiState.detail !is AuthStateDetail.Loading,
                onClick = { viewModel.switchAuthMode() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
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