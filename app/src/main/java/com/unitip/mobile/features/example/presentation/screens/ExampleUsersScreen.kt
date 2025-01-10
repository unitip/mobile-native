package com.unitip.mobile.features.example.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.example.presentation.states.ExampleUsersState
import com.unitip.mobile.features.example.presentation.viewmodels.ExampleUsersViewModel

@Composable
fun ExampleUsersScreen(
    viewModel: ExampleUsersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Button(onClick = { viewModel.getAllUsers() }) {
                Text(text = "Load users")
            }

            with(uiState.detail) {
                when (this) {
                    is ExampleUsersState.Detail.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ExampleUsersState.Detail.Failure -> {
                        Text(text = "Error: $message")
                    }

                    is ExampleUsersState.Detail.Success -> {
                        LazyColumn {
                            items(users) { user ->
                                Column {
                                    Text(text = "Nama: ${user.name}")
                                    Text(text = "Age: ${user.age}")
                                    HorizontalDivider()
                                }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}