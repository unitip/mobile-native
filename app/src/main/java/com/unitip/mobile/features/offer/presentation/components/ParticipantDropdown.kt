package com.unitip.mobile.features.offer.presentation.components

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
import androidx.compose.material3.MaterialTheme
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
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Users

@Composable
fun ParticipantDropdown(
    allowMultiple: Boolean = true,
    value: Int? = null,
    maxValue: Int = 5,
    onValueChange: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        ListItem(
            leadingContent = { Icon(Lucide.Users, contentDescription = null) },
            headlineContent = { Text(text = "Maksimal Peserta") },
            supportingContent = {
                Text(
                    text = when (value != null) {
                        true -> "$value peserta"
                        else -> "Belum ditentukan"
                    }
                )
            },
            trailingContent = {
                if (allowMultiple)
                    Icon(
                        when (isExpanded) {
                            true -> Lucide.ChevronUp
                            else -> Lucide.ChevronDown
                        }, contentDescription = null
                    )
            },
            modifier = Modifier.clickable(enabled = allowMultiple) {
                isExpanded = !isExpanded
            }
        )

        AnimatedVisibility(visible = isExpanded && allowMultiple) {
            Column {
                HorizontalDivider(thickness = .56.dp)
                Array(maxValue) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isExpanded = false
                                onValueChange(it + 1)
                            }
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                    ) {
                        RadioButton(
                            selected = value != null && value - 1 == it,
                            onClick = null
                        )
                        Text(
                            text = "${it + 1} peserta",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}