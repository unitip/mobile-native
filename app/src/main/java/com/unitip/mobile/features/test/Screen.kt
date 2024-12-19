package com.unitip.mobile.features.test

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TestScreen(viewModel: TestViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.value.details) {
        with(uiState.value.details) {
            if (this is TestUiDetails.Success)
                Toast.makeText(context, this.data, Toast.LENGTH_SHORT).show()
            else if (this is TestUiDetails.Failure)
                Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Switch(
                checked = uiState.value.isLogin,
                onCheckedChange = { viewModel.switchAuthMode() },
            )

            Text("list count: ${uiState.value.sharedList.size}")

            if (uiState.value.details is TestUiDetails.Loading)
                CircularProgressIndicator()

            Row {
                Button(onClick = { viewModel.login() }) {
                    Text("Login")
                }
                Button(onClick = { viewModel.fetchData() }) {
                    Text("Fetch data")
                }
            }

            Button(onClick = { isLoading = true }) {
                Text(text = "Show loading")
            }
        }

        // loading placeholder
        Dialog(onDismissRequest = {}) {
            Card {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    strokeCap = StrokeCap.Round
                )
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.undraw_login),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(fraction = .56f)
                    )

                    Text(
                        text = "Mohon tunggu sebentar",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Kami sedang memproses permintaan Anda untuk masuk ke akun Unitip",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}