package com.unitip.mobile.features.chat.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.User
import com.unitip.mobile.features.chat.commons.ChatRoutes
import com.unitip.mobile.features.chat.presentation.viewmodels.ChatsViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.localTimeFormat
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Percakapan",
                    style = MaterialTheme.typography.titleLarge
                )

                CustomIconButton(
                    icon = Lucide.RefreshCw,
                    onClick = { viewModel.getAllRooms() }
                )
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(uiState.rooms) { index, room ->
                    CustomCard(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (index == 0) 16.dp else 8.dp
                        ),
                        onClick = {
                            navController.navigate(
                                ChatRoutes.Conversation(
                                    roomId = room.id,
                                    otherUserId = room.otherUser.id,
                                    otherUserName = room.otherUser.name
                                )
                            )
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
                                    text = room.otherUser.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = when (uiState.session?.id == room.lastSentUserId) {
                                        true -> "Anda: ${room.lastMessage}"
                                        false -> room.lastMessage
                                    },
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Text(
                                room.createdAt.localTimeFormat(),
                                style = MaterialTheme.typography.labelSmall
                            )
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