package com.unitip.mobile.shared.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Lucide

// Data model untuk OrderType
data class OrderType(val name: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTypeOrders(orderTypes: List<OrderType>){
    var expanded by remember { mutableStateOf(false) }
    var selectedOrderType by remember { mutableStateOf<OrderType?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tampilkan item yang dipilih
            TextField(
                value = selectedOrderType?.name ?: "Pilih Tipe Order",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipe Order") },
                trailingIcon = {
                    Icon(
                        imageVector = Lucide.ChevronDown,
                        contentDescription = "Dropdown Arrow"
                    )
                },
                modifier = Modifier.fillMaxWidth()

            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                // List item dari order types

                orderTypes.forEach { orderType ->
                    DropdownMenuItem(
                        text = { Text(orderType.name) },
                        leadingIcon = {
                            Icon(imageVector = orderType.icon, contentDescription = orderType.name)
                        },
                        onClick = {
                            val also: OrderType = orderType.also { selectedOrderType = it }
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

