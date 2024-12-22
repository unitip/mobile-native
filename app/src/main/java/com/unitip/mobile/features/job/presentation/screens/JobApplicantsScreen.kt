package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicantsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Applicants") },
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
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    text = "Berikut beberapa applicants meng-apply job yang Anda buat. Silahkan pilih yang paling sesuai dengan kriteria Anda",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }

            items(10) {
                OutlinedCard(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = if (it == 0) 16.dp else 8.dp
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Rizal Dwi Anggoro",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Rp 5.000,00",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        FilledTonalIconButton(onClick = {}) {
                            Icon(Lucide.Check, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobApplicantsScreenPreview() {
    JobApplicantsScreen()
}