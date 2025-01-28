package com.unitip.mobile.features.location.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView

private val unsGeoPoint = GeoPoint(-7.559843, 110.856658)

@Composable
fun rememberMapView(
    context: Context,
    initialGeoPoint: GeoPoint? = null,
    initialZoom: Double = 18.5,
    onChangeGeoPoint: (geoPoint: GeoPoint) -> Unit = {}
): MapView {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            setMultiTouchControls(true)
            controller.setCenter(initialGeoPoint ?: unsGeoPoint)
            controller.setZoom(initialZoom)

            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

            addMapListener(object : MapListener {
                override fun onScroll(event: ScrollEvent?): Boolean {
                    onChangeGeoPoint(
                        GeoPoint(
                            mapCenter.latitude,
                            mapCenter.longitude
                        )
                    )
                    return true
                }

                override fun onZoom(event: ZoomEvent?): Boolean {
                    onChangeGeoPoint(
                        GeoPoint(
                            mapCenter.latitude,
                            mapCenter.longitude
                        )
                    )
                    return true
                }
            })
        }
    }

    DisposableEffect(mapView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> mapView.onDetach()
                else -> Unit
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}