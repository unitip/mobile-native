package com.unitip.mobile.features.job.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Users

@Composable
fun JobListItem(
    modifier: Modifier = Modifier,
    customerName: String,
    title: String,
    note: String,
    type: String,
    pickupLocation: String,
    destination: String,
) {
    OutlinedCard(modifier = modifier) {
        Column {
            Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)) {
                Text(
                    text = customerName,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            HorizontalDivider()

            // title and note
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = note,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // detail
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                DetailListItem(title = "Type", content = type)
                DetailListItem(title = "Jemput", content = pickupLocation)
                DetailListItem(title = "Tujuan", content = destination)
            }

            // applicants and button apply
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp,
                )
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Lucide.Users, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text(
                        text = "0 applicants",
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Button(onClick = {}) {
                    Text(text = "Apply")
                }
            }
        }
    }
}

@Composable
private fun DetailListItem(
    title: String,
    content: String,
) {
    Row {
        Text(
            text = title,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = content,
            modifier = Modifier.weight(9f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}