package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.ExternalLink
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.MapPinned
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.Wallet
import com.unitip.mobile.features.chat.commons.ChatRoutes
import com.unitip.mobile.features.offer.domain.models.DetailApplicantOffer
import com.unitip.mobile.features.offer.presentation.components.ErrorState
import com.unitip.mobile.features.offer.presentation.states.DetailApplicantOfferState
import com.unitip.mobile.features.offer.presentation.viewmodels.DetailApplicantOfferViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.isDriver
import com.unitip.mobile.shared.commons.extensions.openGoogleMaps
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@Composable
fun DetailApplicantOfferScreen(
    offerId: String,
    applicantId: String,
    viewModel: DetailApplicantOfferViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.updateStatus) {
        when (uiState.updateStatus) {
            is DetailApplicantOfferState.UpdateStatus.Success -> {
                snackbarHostState.showSnackbar("Status berhasil diupdate")
            }

            is DetailApplicantOfferState.UpdateStatus.Failure -> {
                snackbarHostState.showSnackbar(
                    (uiState.updateStatus as DetailApplicantOfferState.UpdateStatus.Failure).message
                )
            }

            else -> {}
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceContainerHigh,
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                        )
                    )
                )
                .padding(it)
        ) {
            // App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomIconButton(
                    onClick = { navController.popBackStack() },
                    icon = Lucide.ChevronLeft
                )
                Spacer(modifier = Modifier.weight(1f))
                CustomIconButton(
                    onClick = { viewModel.fetchData() },
                    icon = Lucide.RefreshCw
                )
                CustomIconButton(
                    onClick = { /* Handle menu */ },
                    icon = Lucide.Menu
                )
            }

            when (val detailState = uiState.detail) {
                is DetailApplicantOfferState.Detail.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(strokeCap = StrokeCap.Round)
                    }
                }

                is DetailApplicantOfferState.Detail.Failure -> {
                    ErrorState(message = detailState.message)
                }

                is DetailApplicantOfferState.Detail.Success -> {
                    LazyColumn(modifier = Modifier.weight(1f), state = listState) {
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = uiState.applicant.customer.name,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                        // Applicant Details
                        itemsIndexed(
                            listOf(
                                mapOf(
                                    "icon" to Lucide.MapPin,
                                    "title" to "Lokasi Penjemputan",
                                    "value" to uiState.applicant.pickupLocation,
                                    "coordinates" to uiState.applicant.pickupCoordinates
                                ),
                                mapOf(
                                    "icon" to Lucide.MapPinned,
                                    "title" to "Lokasi Tujuan",
                                    "value" to uiState.applicant.destinationLocation,
                                    "coordinates" to uiState.applicant.destinationCoordinates
                                ),
                                mapOf(
                                    "icon" to Lucide.Tag,
                                    "title" to "Status",
                                    "value" to uiState.applicant.applicantStatus
                                ),
                                mapOf(
                                    "icon" to Lucide.Wallet,
                                    "title" to "Harga Final",
                                    "value" to "Rp${uiState.applicant.finalPrice}"
                                )
                            )
                        ) { _, item ->
                            DetailItemApplicant(
                                item = item,
                                coordinates = item["coordinates"] as? DetailApplicantOffer.Coordinates
                            )
                        }

                        item {
                            if (uiState.session.isDriver()) {
                                uiState.applicant.customer.id.let { customerId ->
                                    ItemChat(customerId = customerId, viewModel = viewModel)
                                }
                            }
                        }
                    }

                    if (uiState.session.isDriver()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            when (uiState.updateStatus) {
                                is DetailApplicantOfferState.UpdateStatus.Loading -> {
                                    CircularProgressIndicator()
                                }

                                else -> {
                                    StatusButtons(
                                        status = uiState.applicant.applicantStatus,
                                        onUpdateStatus = viewModel::updateStatus
                                    )
                                }
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun ItemChat(
    customerId: String,
    viewModel: DetailApplicantOfferViewModel
) {

    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateToChat) {
        uiState.navigateToChat?.let { (roomId, otherUserId, otherUserName) ->
            navController.navigate(
                ChatRoutes.Conversation(
                    roomId = roomId,
                    otherUserId = otherUserId,
                    otherUserName = otherUserName
                )
            ) {
                launchSingleTop = true
            }
            // Reset state setelah navigasi
            viewModel.resetNavigateToChat()
        }
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            .clickable { viewModel.createChat(customerId) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        Lucide.CircleUser,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Chat",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Chat dengan Driver",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Icon(
                imageVector = Lucide.MessageCircle,
                contentDescription = "Chat",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun StatusButtons(
    status: String,
    onUpdateStatus: (String) -> Unit
) {
    when (status) {
        "pending" -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onUpdateStatus("accepted") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Accept")
                }
                Button(
                    onClick = { onUpdateStatus("rejected") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reject")
                }
            }
        }

        "accepted" -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onUpdateStatus("on_the_way") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Perjalanan")
                }
                Button(
                    onClick = { onUpdateStatus("pending") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pending")
                }
            }
        }

        "rejected" -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onUpdateStatus("pending") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pending")
                }
                Button(
                    onClick = { onUpdateStatus("accepted") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Accept")
                }
            }
        }

        "on_the_way" -> {
            Button(
                onClick = { onUpdateStatus("done") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Set Done")
            }
        }
    }
}

@Composable
private fun DetailItemApplicant(
    item: Map<String, Any>,
    coordinates: DetailApplicantOffer.Coordinates? = null
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            .then(
                if (coordinates != null) {
                    Modifier.clickable {
                        openGoogleMaps(
                            context,
                            coordinates.latitude,
                            coordinates.longitude
                        )
                    }
                } else Modifier
            )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Konten sebelah kiri
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        item["icon"] as ImageVector,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = item["title"] as String,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = item["value"] as String,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Icon external link di sebelah kanan
            if (coordinates != null) {
                Icon(
                    imageVector = Lucide.ExternalLink,
                    contentDescription = "Open in Maps",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
