package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.Users
import com.unitip.mobile.features.home.presentation.components.DashboardDriverApplicationsLoadingPlaceholder
import com.unitip.mobile.features.home.presentation.viewmodels.DashboardDriverViewModel
import com.unitip.mobile.features.offer.commons.OfferRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.toLocalCurrencyFormat
import com.unitip.mobile.features.home.presentation.states.DashboardDriverState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDriverScreen(
    viewModel: DashboardDriverViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ringkasan") },
                    actions = {
                        IconButton(onClick = { viewModel.getDashboard() }) {
                            Icon(
                                Lucide.RefreshCw,
                                contentDescription = null
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(OfferRoutes.Create) }) {
                Icon(Lucide.Plus, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
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

            with(uiState.detail) {
                when (this) {
                    is State.Detail.Loading -> item {
                        DashboardDriverApplicationsLoadingPlaceholder()
                    }

                    is State.Detail.Success -> itemsIndexed(data.applications) { index, application ->
                        if (index > 0)
                            HorizontalDivider(thickness = 0.56.dp)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = if (index == 0) 8.dp else 0.dp)
                                .clickable { }
                                .padding(16.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = application.job.customer.name,
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
                                text = application.job.note,
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
                                    modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp),
                                    tint = ListItemDefaults.colors().supportingTextColor
                                )
                                Text(
                                    text = when (application.bidPrice == application.job.expectedPrice) {
                                        true -> application.job.expectedPrice.toLocalCurrencyFormat()
                                        else -> application.job.expectedPrice.toLocalCurrencyFormat() +
                                                " -> ${application.bidPrice.toLocalCurrencyFormat()}"
                                    },
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            if (application.bidPrice != application.job.expectedPrice)
                                Text(
                                    text = application.bidNote,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = ListItemDefaults.colors().supportingTextColor,
                                    modifier = Modifier.padding(
                                        top = 2.dp,
                                        start = MaterialTheme.typography.bodySmall.fontSize.value.dp + 8.dp
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
                                    modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp),
                                    tint = ListItemDefaults.colors().supportingTextColor
                                )
                                Text(
                                    text = "Menunggu persetujuan",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    else -> Unit
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

            item {
                HorizontalDivider()
                Text(
                    text = "Daftar Penawaran",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    text = "Berikut daftar penawaran yang Anda buat dan masih aktif",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }

            with(uiState.detail) {
                when (this) {
                    is State.Detail.Success -> itemsIndexed(data.offers) { index, offer ->
                        if (index > 0)
                            HorizontalDivider(thickness = 0.56.dp)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = if (index == 0) 8.dp else 0.dp)
                                .clickable {
                                    navController.navigate(
                                        OfferRoutes.DetailOfferCustomer(offerId = offer.id)
                                    )
                                }
                                .padding(16.dp),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = offer.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = offer.description,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Row(
                                    modifier = Modifier.padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Lucide.MapPin,
                                        contentDescription = null,
                                        modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp),
                                        tint = ListItemDefaults.colors().supportingTextColor
                                    )
                                    Text(
                                        text = "${offer.pickupArea} → ${offer.destinationArea}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Lucide.BadgePercent,
                                        contentDescription = null,
                                        modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp),
                                        tint = ListItemDefaults.colors().supportingTextColor
                                    )
                                    Text(
                                        text = offer.price.toLocalCurrencyFormat(),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                // jumlah pendaftar
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Lucide.Users,
                                        contentDescription = null,
                                        modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp),
                                        tint = ListItemDefaults.colors().supportingTextColor
                                    )
                                    Text(
                                        text = "${offer.applicants.size}/${offer.maxParticipants} pendaftar",
                                        style = MaterialTheme.typography.bodySmall
                                    )
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