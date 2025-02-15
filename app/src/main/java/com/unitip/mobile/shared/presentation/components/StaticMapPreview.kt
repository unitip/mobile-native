package com.unitip.mobile.shared.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.RefreshCw
import com.unitip.mobile.shared.presentation.hooks.rememberMapView
import org.osmdroid.util.GeoPoint

@SuppressLint("ClickableViewAccessibility")
@Composable
fun StaticMapPreview(
    modifier: Modifier = Modifier,
    geoPoint: GeoPoint,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val mapView = rememberMapView(
        context = context,
        initialZoom = 20.0,
        isStatic = true,
        initialGeoPoint = geoPoint
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(256.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AndroidView(
            factory = {
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

            FilledIconButton(
                onClick = {
                    mapView.invalidate()
                    mapView.tileProvider.clearTileCache()
                    mapView.controller.setZoom(18.0)
                    mapView.controller.zoomTo(20.0)
                    mapView.controller.animateTo(geoPoint)
                }
            ) {
                Icon(
                    Lucide.RefreshCw,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}