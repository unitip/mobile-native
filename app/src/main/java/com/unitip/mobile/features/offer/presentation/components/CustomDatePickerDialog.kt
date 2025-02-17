package com.unitip.mobile.features.offer.presentation.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    initialSelectedDateMillis: Long? = null,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (state.selectedDateMillis != null)
                    onConfirm(state.selectedDateMillis!!)
                else
                    onDismiss()
            }) {
                Text(text = "Selesai")
            }
        }
    ) {
        DatePicker(state)
    }
}