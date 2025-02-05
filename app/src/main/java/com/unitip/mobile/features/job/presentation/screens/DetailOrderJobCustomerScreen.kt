package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailOrderJobCustomerScreen() {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "halaman detail order job untuk customer")
            Text(text = "di halaman ini customer bisa milihat daftar application dan approve salah satu application, serta melihat detail job")
        }
    }
}