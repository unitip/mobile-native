package com.unitip.mobile.shared.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.shared.commons.constants.ServiceTypeConstant

@Composable
fun ServiceTypeDropdown(
    value: ServiceTypeConstant? = null,
    onConfirm: (ServiceTypeConstant) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        ListItem(
            leadingContent = { Icon(Lucide.Bike, contentDescription = null) },
            headlineContent = { Text(text = "Jenis Layanan") },
            supportingContent = {
                Text(
                    text = when (value != null) {
                        true -> value.subtitle
                        else -> "Tidak ditentukan"
                    }
                )
            },
            trailingContent = { Icon(Lucide.ChevronDown, contentDescription = null) },
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        )

        AnimatedVisibility(visible = isExpanded) {
            Column {
                HorizontalDivider(thickness = .56.dp)

                ServiceTypeConstant.entries.map {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isExpanded = false
                                onConfirm(it)
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        RadioButton(selected = value == it, onClick = null)
                        Text(text = it.title)
                    }
                }
            }
        }
    }
}