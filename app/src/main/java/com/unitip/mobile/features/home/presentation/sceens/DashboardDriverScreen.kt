package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardDriverScreen() {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "ini dashboard untuk driver")
        }
    }
}