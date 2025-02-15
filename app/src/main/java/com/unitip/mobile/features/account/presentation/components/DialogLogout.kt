package com.unitip.mobile.features.account.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun DialogLogout(
    isLoading: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            if (!isLoading) onDismiss()
        },
        title = { Text(text = "Keluar") },
        text = { Text(text = "Apakah Anda yakin akan keluar dari akun Unitip?") },
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = !isLoading) {
                Box {
                    Text(
                        text = "Keluar",
                        modifier = Modifier.alpha(if (isLoading) 0f else 1f)
                    )
                    if (isLoading)
                        CircularProgressIndicator(
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(ButtonDefaults.IconSize)
                                .align(Alignment.Center)
                        )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) {
                Text(text = "Batal")
            }
        }
    )
}