package com.unitip.mobile.features.chat.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.unitip.mobile.features.chat.commons.ChatRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomCard

@Composable
fun ChatsScreen() {
    val navController = LocalNavController.current

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceContainerHigh,
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                        )
                    )
                )
                .padding(it)
        ) {
            Text(
                text = "Percakapan",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(5) {
                    CustomCard(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (it == 0) 16.dp else 8.dp
                        ),
                        onClick = {
                            navController.navigate(ChatRoutes.Conversation)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Icon(
                                    Lucide.User,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.Center),
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp, end = 8.dp)
                            ) {
                                Text(
                                    "Rizal Anggooro",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "${if (it % 2 == 0) "Anda: " else ""}how are you?",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Text("10.10", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                item {
                    Text(
                        text = "Percakapan akan otomatis terhapus ketika pesanan telah selesai dilakukan",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}