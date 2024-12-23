package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJobScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Job") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Lucide.ArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Text(
                    text = "Judul job yang dibuat oleh customer",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Catatan yang diberikan oleh customer ke driver",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline)
                )
            }

            HorizontalDivider()

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(text = "Daftar Applicants", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}