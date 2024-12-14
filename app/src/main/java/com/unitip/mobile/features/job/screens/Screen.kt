package com.unitip.mobile.features.job.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unitip.mobile.core.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    onNavigate: (route: Any) -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.padding(bottom = 0.dp),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Red),
                title = {
                    Text("Jobs")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate(Routes.CreateJob) },
            ) {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }
        },
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            // summary of online driver count
            item {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Online Drivers",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "Terdapat 12 orang driver yang sedang aktif menggunakan aplikasi Unitip",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            // list of jobs
            item {
                Text(
                    text = "Berikut beberapa jobs yang menunggu untuk diambil oleh driver",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            items(20) {
                JobItem(index = it)
            }
        }
    }
}

@Composable
private fun JobItem(index: Int) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(text = "$index. Rizal Dwi Anggoro")
            }
        }
    }
}