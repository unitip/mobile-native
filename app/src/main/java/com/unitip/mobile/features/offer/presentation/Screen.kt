package com.unitip.mobile.features.offer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Offers")
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text("Offers screen")
        }
    }
}