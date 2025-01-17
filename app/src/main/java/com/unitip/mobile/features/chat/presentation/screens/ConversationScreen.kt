package com.unitip.mobile.features.chat.presentation.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Send
import com.unitip.mobile.features.chat.presentation.components.BubbleMessage
import com.unitip.mobile.features.chat.presentation.components.BubbleMessageType
import com.unitip.mobile.features.chat.presentation.viewmodels.ConversationViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomIconButton

private data class ChatItem(
    val from: String,
    val message: String,
)

@Composable
fun ConversationScreen(
    viewModel: ConversationViewModel = hiltViewModel(),
    toUserId: String,
    toUserName: String
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    var message by remember { mutableStateOf(TextFieldValue("")) }
    var messages = remember { mutableStateListOf<ChatItem>() }

    BackHandler {
        navController.popBackStack()
        Toast.makeText(context, "back called", Toast.LENGTH_SHORT).show()
    }

    Scaffold { paddingValues ->
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
                .padding(paddingValues)
                .imePadding()
        ) {
            Row(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomIconButton(
                    icon = Lucide.ChevronLeft,
                    onClick = { navController.popBackStack() }
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = toUserName, style = MaterialTheme.typography.titleMedium)
                    Text(text = "mengetik...", style = MaterialTheme.typography.bodySmall)
                }
            }

            HorizontalDivider()

            // Chat Messages List
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                data class Test(
                    val message: String,
                    val type: BubbleMessageType
                )

                val messages =
                    listOf(
                        Test(
                            message = "Halo, selamat siang mas Rizal!",
                            type = BubbleMessageType.SENDER
                        ),
                        Test(
                            message = "Tolong cepat sesuai titik yang saya kirim ya mas. Saya buru2 ketemu dengan dosen",
                            type = BubbleMessageType.SENDER
                        ),
                        Test(
                            message = "Nggih, mba!", type = BubbleMessageType.RECEIVER
                        ),
                        Test(
                            message = "Mas??", type = BubbleMessageType.SENDER
                        ),
                        Test(
                            message = "Sabar mba, numpak buroq wae nek kesusu! Telat kok ngerusuhi wong liyo!!",
                            type = BubbleMessageType.RECEIVER
                        ),
                        Test(
                            message = "SABAR ANJG!!!", type = BubbleMessageType.RECEIVER
                        ),
                        Test(
                            message = "kok galak :(", type = BubbleMessageType.SENDER
                        ),
                    )

                itemsIndexed(messages) { index, message ->
                    BubbleMessage(
                        modifier = Modifier.padding(
                            top = if (index == 0) 16.dp else (
                                    if (messages[index - 1].type == message.type) 4.dp
                                    else 12.dp)
                        ),
                        type = message.type,
                        message = message.message
                    )
                }

//                itemsIndexed(uiState.messages) { index, message ->
//                    Text(text = message.message)
//                }
            }

            HorizontalDivider()

            // input message
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = { Text(text = "Ketik pesan...") },
                    maxLines = 5,
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { }
                ) {
                    Icon(
                        Lucide.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
