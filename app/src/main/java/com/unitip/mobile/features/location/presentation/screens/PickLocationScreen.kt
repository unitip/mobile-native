package com.unitip.mobile.features.location.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

val unsGeoPoint = GeoPoint(-7.559843, 110.856658)

@Composable
fun PickLocationScreen() {
    var currentGeoPoint by remember { mutableStateOf(GeoPoint(0.0, 0.0)) }

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
                    onClick = {}
                )
                Text(text = "Pilih Lokasi", style = MaterialTheme.typography.titleLarge)
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
                    factory = { context ->
                        val mapView = MapView(context).apply {
                            setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                            setMultiTouchControls(true)

                            controller.setCenter(unsGeoPoint)
                            controller.setZoom(18.5)
                        }
                        mapView.addMapListener(object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                currentGeoPoint = GeoPoint(
                                    mapView.mapCenter.latitude,
                                    mapView.mapCenter.longitude
                                )
                                return true
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                currentGeoPoint = GeoPoint(
                                    mapView.mapCenter.latitude,
                                    mapView.mapCenter.longitude
                                )
                                return true
                            }
                        })
                        mapView
                    }
                )

                // marker penanda
                Icon(
                    Lucide.MapPin,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            HorizontalDivider()

            Text(
                text = "${currentGeoPoint.latitude},${currentGeoPoint.longitude}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            // button confirm
            Button(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Selesai")
            }
        }
    }
}