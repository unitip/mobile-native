package com.unitip.mobile.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmBottomSheet(
    sheetState: SheetState,
    image: Painter? = null,
    title: String,
    subtitle: String,
    positiveText: String,
    negativeText: String,
    onPositive: () -> Unit = {},
    onNegative: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onNegative,
        sheetState = sheetState,
        windowInsets = BottomSheetDefaults.windowInsets.only(
            sides = WindowInsetsSides.Bottom,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
        ) {
            if (image != null)
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(fraction = .48f)
                )

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = (if (image != null) 32 else 0).dp)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .alpha(.8f)
                    .padding(top = 4.dp)
            )

            Button(
                onClick = onPositive,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text(text = positiveText)
            }
            TextButton(
                onClick = onNegative,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = negativeText)
            }
        }
    }
}