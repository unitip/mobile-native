package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.FastForward
import com.composables.icons.lucide.FileWarning
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.DropdownTypeOrders
import com.unitip.mobile.shared.presentation.components.OrderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOfferScreen() {
    val navController = LocalNavController.current
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Offer") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                placeholder = {
                    Text(text = "Judul penawaran")
                })
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                val orderTypes = listOf(
                    OrderType("Jasa Titip", Lucide.Check),
                    OrderType("Antar Jemput", Lucide.FileWarning),
                    OrderType("Lain - lain", Lucide.FastForward)
                )
                // Panggil DropdownTypeOrders di sini
                DropdownTypeOrders(orderTypes = orderTypes)
            }
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Biaya penanganan")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Lokasi Penawaran")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Area Pengantaran")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Catatan tambahan")
                },
                minLines = 5,
            )

            // available until
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Rounded.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .size(40.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Tersedia hingga") },
                supportingContent = { Text(text = "16/12/2024, 10.10") },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { }
            )

            // type
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Rounded.Done,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .size(40.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Jenis penawaran") },
                supportingContent = { Text(text = "Delivery") },
                modifier = Modifier
                    .clickable { }
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
            ) {
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text(text = "Batal")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {}) {
                    Text(text = "Create")
                }
            }
        }
    }
}