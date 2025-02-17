package com.unitip.mobile.features.offer.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    initialHour: Int? = null,
    initialMinute: Int? = null,
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit
) {
    val calendar = Calendar.getInstance()
    val state = rememberTimePickerState(
        initialHour = initialHour ?: calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = initialMinute ?: calendar.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(state) }) {
                Text(text = "Selesai")
            }
        },
        text = { TimePicker(state) }
    )
}