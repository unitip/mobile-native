package com.unitip.mobile.features.job.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Users
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@Composable
fun JobListItem(
    modifier: Modifier = Modifier,
    customerName: String,
    title: String,
    note: String,
    type: String,
    pickupLocation: String,
    destination: String,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = .08f))
    ) {
        Column {
            // customer profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onSurface)
                        .size(20.dp)
                ) {
                    Icon(
                        Lucide.User,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = customerName,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            // title and note
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = note,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                CustomIconButton(
                    icon = if (isExpanded) Lucide.ChevronUp else Lucide.ChevronDown,
                    onClick = {
                        isExpanded = !isExpanded
                    }
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    HorizontalDivider()

                    // detail
                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                        DetailListItem(title = "Type", content = type)
                        DetailListItem(title = "Jemput", content = pickupLocation)
                        DetailListItem(title = "Tujuan", content = destination)
                    }

                    // applicants and button apply
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp,
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Icon(
                                    Lucide.Users,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Text(
                                text = "0 applicants",
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailListItem(
    title: String,
    content: String,
) {
    Row {
        Text(
            text = title,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = content,
            modifier = Modifier.weight(9f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}