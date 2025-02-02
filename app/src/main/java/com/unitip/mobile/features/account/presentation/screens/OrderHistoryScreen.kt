package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.account.presentation.states.OrderHistoryState
import com.unitip.mobile.features.account.presentation.viewmodels.OrderHistoryViewModel
import com.unitip.mobile.shared.commons.extensions.localDateFormat

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "ini halaman riwayat order")
            Button(
                onClick = {
                    viewModel.getOrderHistories()
                }
            ) {
                Text(text = "Refresh")
            }

            with(uiState.detail) {
                when (this) {
                    is OrderHistoryState.Detail.Loading -> CircularProgressIndicator()

                    is OrderHistoryState.Detail.Success -> LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(orders) {
                            ListItem(
                                overlineContent = { Text(text = it.id) },
                                headlineContent = { Text(text = it.title) },
                                supportingContent = { Text(text = it.createdAt.localDateFormat()) }
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}