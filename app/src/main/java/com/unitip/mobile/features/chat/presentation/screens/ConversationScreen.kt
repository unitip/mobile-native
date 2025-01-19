package com.unitip.mobile.features.chat.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imeAnimationSource
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.Send
import com.unitip.mobile.features.chat.presentation.components.BubbleMessage
import com.unitip.mobile.features.chat.presentation.components.BubbleMessageSendStatus
import com.unitip.mobile.features.chat.presentation.components.BubbleMessageType
import com.unitip.mobile.features.chat.presentation.states.ConversationState
import com.unitip.mobile.features.chat.presentation.viewmodels.ConversationViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@SuppressLint("NewApi")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConversationScreen(
    roomId: String,
    otherUserName: String,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }

    val isImeVisible = WindowInsets.isImeVisible
    val imeBottomSource = WindowInsets.imeAnimationSource.getBottom(LocalDensity.current)
    val imeBottomTarget = WindowInsets.imeAnimationTarget.getBottom(LocalDensity.current)

    /**
     * launched effect untuk mendeteksi ime input keyboard,
     * lakukan scroll ke pesan terakhir ketika keyboard
     * telah ditampilkan secara penuh
     */
    LaunchedEffect(isImeVisible, imeBottomSource, imeBottomTarget) {
        if (isImeVisible && imeBottomSource > 0 &&
            imeBottomTarget > 0 &&
            imeBottomSource == imeBottomTarget &&
            uiState.messages.isNotEmpty()
        ) listState.scrollToItem(index = uiState.messages.size - 1)
    }

    /**
     * launched effect untuk membuka koneksi mqtt serta menerima
     * history pesan dari database
     */
    LaunchedEffect(toUserId) {
        viewModel.openRealtimeConnection(otherUserId = toUserId)
        viewModel.getAllMessages(fromUserId = toUserId)
    }

    /**
     * launched effect untuk scroll pesan ke bagian akhir
     * ketika history pesan berhasil diterima dari database
     */
    LaunchedEffect(uiState.detail) {
        if (uiState.detail is ConversationState.Detail.Success)
            listState.scrollToItem(index = uiState.messages.size - 1)
    }

    /**
     * launched effect untuk scroll pesan ke bagian akhir
     * ketika terdapat terdapat pesan baru, baik dari other
     * maupun dari current user
     */
    LaunchedEffect(uiState.realtimeDetail) {
        with(uiState.realtimeDetail) {
            if (this is ConversationState.RealtimeDetail.NewMessage &&
                uiState.messages.isNotEmpty()
            ) {
                listState.scrollToItem(index = uiState.messages.size - 1)
                viewModel.resetRealtimeState()
            }
        }
    }

//    BackHandler {
//        navController.popBackStack()
//        Toast.makeText(context, "back called", Toast.LENGTH_SHORT).show()
//    }

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
                    AnimatedVisibility(visible = uiState.isOtherUserTyping) {
                        Text(text = "mengetik...", style = MaterialTheme.typography.bodySmall)
                    }
                }
                CustomIconButton(
                    icon = Lucide.RefreshCw,
                    onClick = { viewModel.getAllMessages(fromUserId = toUserId) }
                )
            }

            HorizontalDivider()

            // list messages
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                itemsIndexed(uiState.messages) { index, message ->
                    val isReceiver = message.fromUserId == toUserId

                    BubbleMessage(
                        modifier = Modifier.padding(
                            top = if (index == 0) 16.dp else (
                                    if (uiState.messages[index - 1].toUserId == message.toUserId) 4.dp
                                    else 12.dp),
                            bottom = if (index == uiState.messages.size - 1) 16.dp
                            else 0.dp
                        ),
                        type = if (isReceiver) BubbleMessageType.RECEIVER
                        else BubbleMessageType.SENDER,
                        message = message.message,
                        sendStatus = if (uiState.sendingMessageUUIDs.contains(message.id)) BubbleMessageSendStatus.SENDING
                        else if (uiState.failedMessageUUIDs.contains(message.id)) BubbleMessageSendStatus.FAILED
                        else BubbleMessageSendStatus.SENT,
                        createdAt = message.createdAt
                    )
                }
            }

            HorizontalDivider()

            // input message
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.imePadding()
            ) {
                TextField(
                    value = message,
                    onValueChange = {
                        message = it

                        val newIsTyping = it.isNotBlank()
                        if (uiState.isTyping != newIsTyping)
                            viewModel.notifyTypingStatus(isTyping = newIsTyping)
                    },
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
                        .clickable {
                            if (message.isNotBlank()) {
                                viewModel.sendMessage(
                                    toUserId = toUserId,
                                    message = message
                                )
                                message = ""
                            }
                        }
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
