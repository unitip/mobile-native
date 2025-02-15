package com.unitip.mobile.features.job.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composables.icons.lucide.BadgePercent
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Send
import com.composables.icons.lucide.X
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyJobBottomSheet(
    onSend: (withOffer: Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
        },
        sheetState = sheetState
    ) {
        Column {
            ListItem(
                leadingContent = { Icon(Lucide.Send, contentDescription = null) },
                headlineContent = { Text(text = "Langsung kirim") },
                colors = ListItemDefaults.colors(containerColor = Color.Unspecified),
                modifier = Modifier
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { onSend(false) }
                    }
            )
            ListItem(
                leadingContent = { Icon(Lucide.BadgePercent, contentDescription = null) },
                headlineContent = { Text(text = "Kirim penawaran") },
                colors = ListItemDefaults.colors(containerColor = Color.Unspecified),
                modifier = Modifier
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { onSend(true) }
                    }
            )
            ListItem(
                leadingContent = { Icon(Lucide.X, contentDescription = null) },
                headlineContent = { Text(text = "Batal") },
                colors = ListItemDefaults.colors(containerColor = Color.Unspecified),
                modifier = Modifier
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
                    }
            )
        }
    }
}