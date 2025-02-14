package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.BadgePercent
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.Tag
import com.unitip.mobile.features.home.presentation.viewmodels.DashboardDriverViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDriverScreen(
    viewModel: DashboardDriverViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ringkasan") },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                Lucide.RefreshCw,
                                contentDescription = null
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    text = "Daftar Lamaran",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    text = "Berikut beberapa lamaran pekerjaan yang sudah Anda kirimkan dan menunggu persetujuan dari customer",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }

            items(3) {
                if (it > 0)
                    HorizontalDivider(thickness = 0.56.dp)
                Column(modifier = Modifier
                    .padding(top = if (it == 0) 16.dp else 0.dp)
                    .clickable { }
                    .padding(16.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Rizal Dwi Anggoro",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Antar jemput",
                            style = MaterialTheme.typography.bodySmall,
                            color = ListItemDefaults.colors().supportingTextColor
                        )
                    }
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Lucide.BadgePercent,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.typography.bodyMedium.fontSize.value.dp),
                            tint = ListItemDefaults.colors().supportingTextColor
                        )
                        Text(
                            text = "Rp 100.000 -> Rp 110.000",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = "Karena lumayan jauh dari rumah saya dan saya menggunakan motor gede jadi bensinnya lebih mahal dibandingkan menggunakan motor matic!",
                        style = MaterialTheme.typography.bodySmall,
                        color = ListItemDefaults.colors().supportingTextColor,
                        modifier = Modifier.padding(
                            top = 2.dp,
                            start = MaterialTheme.typography.bodyMedium.fontSize.value.dp + 8.dp
                        )
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Lucide.Tag,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.typography.bodyMedium.fontSize.value.dp),
                            tint = ListItemDefaults.colors().supportingTextColor
                        )
                        Text(
                            text = "Menunggu persetujuan",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item {
                HorizontalDivider()
                Text(
                    text = "Daftar Pekerjaan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    text = "Berikut beberapa pekerjaan yang sudah Anda terima dan sedang dalam proses pengerjaan",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}