package com.unitip.mobile.features.location.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.LocateFixed
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.ZoomIn
import com.composables.icons.lucide.ZoomOut
import com.unitip.mobile.features.location.presentation.states.PickLocationState
import com.unitip.mobile.features.location.presentation.viewmodels.PickLocationViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import com.unitip.mobile.shared.presentation.hooks.rememberMapView
import org.osmdroid.util.GeoPoint

val unsGeoPoint = GeoPoint(-7.559843, 110.856658)

@Composable
fun PickLocationScreen(
    resultKey: String,
    initialLatitude: Double? = null,
    initialLongitude: Double? = null,
    viewModel: PickLocationViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    var currentGeoPoint by remember { mutableStateOf(unsGeoPoint) }
    val uiState by viewModel.uiState.collectAsState()

    val mapView = rememberMapView(
        context = context,
        initialGeoPoint = when (initialLatitude != null && initialLongitude != null) {
            true -> GeoPoint(initialLatitude, initialLongitude)
            else -> null
        },
        onChangeGeoPoint = {
            currentGeoPoint = it
        }
    )

    with(uiState.getLastLocationDetail) {
        when (this) {
            is PickLocationState.GetLastLocationDetail.SuccessGetLastLocation -> {
                mapView.controller.animateTo(
                    GeoPoint(
                        lastLocation.latitude,
                        lastLocation.longitude
                    )
                )
                viewModel.resetLastLocationState()
            }

            else -> Unit
        }
    }

    Scaffold {
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
            // app bar
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIconButton(
                    icon = Lucide.ChevronLeft,
                    onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(resultKey, null)
                        navController.popBackStack()
                    }
                )
                Column {
                    Text(text = "Pilih Lokasi", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "${currentGeoPoint.latitude},${currentGeoPoint.longitude}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            HorizontalDivider()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RectangleShape)
            ) {
                // mapview
                AndroidView(
                    factory = {
                        // circle zone radius
//                        val circle = Polygon().apply {
//                            points = Polygon.pointsAsCircle(unsGeoPoint, 2000.0)
//                            outlinePaint.strokeWidth = 4f
//                            outlinePaint.color = Color.Red.toArgb()
//                        }
//                        mapView.overlays.add(circle)
//
//                        val circle2 = Polygon().apply {
//                            points = Polygon.pointsAsCircle(unsGeoPoint, 4000.0)
//                            outlinePaint.strokeWidth = 4f
//                            outlinePaint.color = Color.Blue.toArgb()
//                        }
//                        mapView.overlays.add(circle2)

                        mapView
                    }
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    // marker penanda
                    Icon(
                        Lucide.MapPin,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    // button get last location
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp)
                    ) {
                        FilledTonalIconButton(
                            onClick = {
                                viewModel.getLastLocation()
                            }) {
                            Icon(
                                Lucide.LocateFixed,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        FilledTonalIconButton(
                            onClick = {
                                mapView.controller.zoomIn()
                            }
                        ) {
                            Icon(
                                Lucide.ZoomIn,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        FilledTonalIconButton(
                            onClick = {
                                mapView.controller.zoomOut()
                            }
                        ) {
                            Icon(
                                Lucide.ZoomOut,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider()

            // button confirm
            Button(
                onClick = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        resultKey,
                        currentGeoPoint
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Selesai")
            }
        }
    }
}