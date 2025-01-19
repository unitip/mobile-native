package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.offer.commons.OfferRoutes
import com.unitip.mobile.features.offer.presentation.components.OfferCardItem
import com.unitip.mobile.features.offer.presentation.viewmodels.OffersViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.isDriver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(
    viewModel: OffersViewModel = hiltViewModel()
) {
    // digunakan untuk mengambil instance NavController untuk navigasi antar layar
    val navController = LocalNavController.current
    // untuk ambil context yang digunakan untuk Toast
    // context digunakan untuk memberitahu dimana toast harus ditampilkan
    val context = LocalContext.current
    // mengamati perubahan uiState dari ViewModel untuk pembaharuan UI otomatis
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
            WindowInsetsSides.Top
        ),

        topBar = {
            TopAppBar(
                title = {
                    Text("Offers")
                }
            )
        },
        floatingActionButton = {
            // Floating add ini nantinya akan muncul jika
            // session yang login merupakan driver
            if (uiState.session.isDriver()){
                FloatingActionButton(
                    onClick = { navController.navigate(OfferRoutes.Create) }
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "Create offer"
                    )
                }
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

