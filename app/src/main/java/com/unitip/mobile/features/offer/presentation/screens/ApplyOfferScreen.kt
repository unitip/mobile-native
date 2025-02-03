package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.location.commons.LocationRoutes
import com.unitip.mobile.features.offer.presentation.states.ApplyOfferState
import com.unitip.mobile.features.offer.presentation.viewmodels.ApplyOfferViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.GetPopResult
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import com.unitip.mobile.shared.presentation.components.StaticMapPreview
import org.osmdroid.util.GeoPoint

@Composable
fun ApplyOfferScreen(
    offerId: String,
    viewModel: ApplyOfferViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavController.current

    navController.GetPopResult<GeoPoint>(key = "pickupLocationGeoPoint") {
        if (it != null) viewModel.onPickupLocationGeoPointChange(it)
    }

    navController.GetPopResult<GeoPoint>(key = "destinationLocationGeoPoint") {
        if (it != null) viewModel.onDestinationLocationGeoPointChange(it)
    }

    // Navigate back ketika detail success
    LaunchedEffect(state.detail) {
        if (state.detail is ApplyOfferState.Detail.Success) {
            onNavigateBack()
        }
    }

    Scaffold { paddingValues ->
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
                .padding(paddingValues)
        ) {
            // Custom App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIconButton(
                    onClick = onNavigateBack,
                    icon = Lucide.ChevronLeft
                )

                Text(
                    text = "Ajukan Penawaran",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),

                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp, 0.dp, 16.dp, 8.dp)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = state.note,
                    onValueChange = viewModel::onNoteChange,
                    label = { Text("Catatan") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.pickupLocation,
                    onValueChange = viewModel::onPickupLocationChange,
                    label = { Text("Lokasi Penjemputan") },
                    modifier = Modifier.fillMaxWidth()
                )

                when (state.pickupLocationGeoPoint) {
                    null -> Button(
                        onClick = {
                            navController.navigate(
                                LocationRoutes.PickLocation(
                                    resultKey = "pickupLocationGeoPoint"
                                )
                            )
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = "Pilih lokasi penjemputan")
                    }
                    else -> StaticMapPreview(
                        modifier = Modifier.padding(top = 8.dp),
                        geoPoint = state.pickupLocationGeoPoint,
                        onClick = {
                            navController.navigate(
                                LocationRoutes.PickLocation(
                                    resultKey = "pickupLocationGeoPoint",
                                    initialLatitude = state.pickupLocationGeoPoint.latitude,
                                    initialLongitude = state.pickupLocationGeoPoint.longitude
                                )
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.destinationLocation,
                    onValueChange = viewModel::onDestinationLocationChange,
                    label = { Text("Lokasi Tujuan") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Tambahkan pemilihan lokasi tujuan
                when (state.destinationLocationGeoPoint) {
                    null -> Button(
                        onClick = {
                            navController.navigate(
                                LocationRoutes.PickLocation(
                                    resultKey = "destinationLocationGeoPoint"
                                )
                            )
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = "Pilih lokasi tujuan")
                    }
                    else -> StaticMapPreview(
                        modifier = Modifier.padding(top = 8.dp),
                        geoPoint = state.destinationLocationGeoPoint,
                        onClick = {
                            navController.navigate(
                                LocationRoutes.PickLocation(
                                    resultKey = "destinationLocationGeoPoint",
                                    initialLatitude = state.destinationLocationGeoPoint.latitude,
                                    initialLongitude = state.destinationLocationGeoPoint.longitude
                                )
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.applyOffer(offerId) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.detail !is ApplyOfferState.Detail.Loading
                ) {
                    if (state.detail is ApplyOfferState.Detail.Loading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("Ajukan")
                    }
                }

                if (state.detail is ApplyOfferState.Detail.Error) {
                    Text(
                        text = (state.detail as ApplyOfferState.Detail.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
