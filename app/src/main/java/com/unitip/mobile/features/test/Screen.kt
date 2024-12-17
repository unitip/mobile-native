package com.unitip.mobile.features.test

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestScreen(viewModel: TestViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current

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
        }
    }
}