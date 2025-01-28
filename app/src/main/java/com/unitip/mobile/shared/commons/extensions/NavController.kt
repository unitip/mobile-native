package com.unitip.mobile.shared.commons.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetPopResult(
    key: String,
    onResult: (T?) -> Unit
) {
    val currentBackStackEntry = this.currentBackStackEntry
    if (currentBackStackEntry != null) {
        val result = currentBackStackEntry
            .savedStateHandle
            .getStateFlow<T?>(key, null)
            .collectAsState()

        /**
         * notify callback ketika data sudah berhasil diambil
         * dari screen sebelumnya
         */
        onResult(result.value)

        /**
         * hapus data dari savedStateHandle setelah di consume
         * agar tidak terjadi memory leak
         */
        currentBackStackEntry.savedStateHandle.remove<T>(key)
    }
}