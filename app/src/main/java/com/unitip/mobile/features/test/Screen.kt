package com.unitip.mobile.features.test

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.CircleDot
import com.composables.icons.lucide.Lucide
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun TestScreen(viewModel: TestViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var markerPosition by remember {
        mutableStateOf(GeoPoint(-7.5634, 110.8559))
    }

//    val mapEventsReceiver = object : MapEventsReceiver {
//        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
//            if (p != null) {
//                Toast.makeText(context, "${p.latitude},${p.longitude}", Toast.LENGTH_SHORT).show()
//                markerPosition = p
//            }
//            return true
//        }
//
//        override fun longPressHelper(p: GeoPoint?): Boolean {
//            return true
//        }
//    }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "${markerPosition.latitude},${markerPosition.longitude}")
            Spacer(modifier = Modifier.height(56.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth(),
                    factory = { context ->
                        val mapView = MapView(context).apply {
                            setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                            setMultiTouchControls(true)

                            controller.setCenter(GeoPoint(-7.5634, 110.8559))
                            controller.setZoom(20.0)
                            Log.d("MapView", "TestScreen: ${this.mapCenter}")

                            // Add marker

                        }
//                    mapView.overlays.add(MapEventsOverlay(mapEventsReceiver))
                        mapView.addMapListener(object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                markerPosition = GeoPoint(
                                    mapView.mapCenter.latitude,
                                    mapView.mapCenter.longitude
                                )
                                return true
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                markerPosition = GeoPoint(
                                    mapView.mapCenter.latitude,
                                    mapView.mapCenter.longitude
                                )
                                return true
                            }
                        })
                        mapView
                    },
//                update = { mapView ->
//                    val marker = Marker(mapView).apply {
//                        position = mapView.mapCenter as GeoPoint
//                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                        title = "My Location"
//                        snippet = "This is my location"
//                    }
//
//                    mapView.overlays.clear()
////                    mapView.overlays.add(MapEventsOverlay(mapEventsReceiver))
//                    mapView.overlays.add(marker)
//                }
                )

                // center marker
                Icon(
                    Lucide.CircleDot,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}