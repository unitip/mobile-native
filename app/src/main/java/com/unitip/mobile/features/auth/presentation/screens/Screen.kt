package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R
import com.unitip.mobile.core.ui.UIStatus
import com.unitip.mobile.core.ui.theme.UnitipTheme
import com.unitip.mobile.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(
                    if (isLogin) R.string.login_title
                    else R.string.register_title
                ),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                ),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(
                    if (isLogin) R.string.login_subtitle
                    else R.string.register_subtitle
                ),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )

            // form
            if (!isLogin) TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                    ),
                placeholder = {
                    Text(stringResource(R.string.name_placeholder))
                }
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = (if (isLogin) 16 else 8).dp,
                    ),
                placeholder = {
                    Text(stringResource(R.string.email_placeholder))
                }
            )
            TextField(
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
            if (!isLogin) TextField(
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



            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp, bottom = 8.dp, top = 16.dp)
            ) {
                Button(
                    enabled = uiState.status != UIStatus.Loading,
                    onClick = {
                        with(uiState) {
                            if (status != UIStatus.Loading) {
                                if (isLogin) viewModel.login()
                                else viewModel.register()
                            }
                        }
                    }) {
                    if (uiState.status == UIStatus.Loading)
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
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                TextButton(
                    enabled = uiState.status != UIStatus.Loading,
                    onClick = {
                        isLogin = !isLogin
                    },
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

@Preview
@Composable
private fun AuthScreenPreview() {
    UnitipTheme {
        AuthScreen()
    }
}