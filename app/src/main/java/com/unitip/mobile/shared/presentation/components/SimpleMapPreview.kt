package com.unitip.mobile.shared.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView

@SuppressLint("ClickableViewAccessibility")
@Composable
fun SimpleMapPreview(
    modifier: Modifier = Modifier,
    geoPoint: GeoPoint,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AndroidView(
            factory = { context ->
                val mapView = MapView(context).apply {
                    setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

                    zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
                    controller.setZoom(20.0)
                    controller.setCenter(geoPoint)

                    setMultiTouchControls(false)
                    isClickable = false
                    isFocusable = false
                    setOnTouchListener { _, _ -> true }
                }
                mapView
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        ) {
            Icon(
                Lucide.MapPin,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                tint = Color.Red
            )
        }
    }
}