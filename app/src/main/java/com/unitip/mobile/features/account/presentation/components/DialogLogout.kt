package com.unitip.mobile.features.account.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DialogLogout(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Keluar") },
        text = { Text(text = "Apakah Anda yakin akan keluar dari akun Unitip?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Keluar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        }
    )
}