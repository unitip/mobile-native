package com.unitip.mobile.features.offer.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unitip.mobile.core.navigation.Routes
import com.unitip.mobile.features.offer.components.OfferCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(
    onNavigate: (Any) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text("Offers")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate(Routes.CreateOffer) }
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Create offer"
                )
            }
        },
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    text = "Berikut beberapa penawaran oleh driver dan mitra yang bisa kamu ikuti",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                )
            }

            items(10) { index ->
                Spacer(
                    modifier = Modifier.height(
                        if (index == 0) 16.dp
                        else 8.dp
                    )
                )
                OfferCardItem()
                if (index == 9)
                    Spacer(modifier = Modifier.height((56 + 32).dp))
            }
        }
    }
}