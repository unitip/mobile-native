package com.unitip.mobile.features.chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Send
import com.unitip.mobile.shared.commons.extensions.localDateFormat
import com.unitip.mobile.shared.commons.extensions.localTimeFormat

enum class BubbleMessageType {
    SENDER,
    RECEIVER
}

enum class BubbleMessageSendStatus {
    SENDING,
    SENT,
    FAILED
}

@Composable
fun BubbleMessage(
    modifier: Modifier = Modifier,
    type: BubbleMessageType = BubbleMessageType.SENDER,
    message: String = "",
    sendStatus: BubbleMessageSendStatus = BubbleMessageSendStatus.SENDING,
    createdAt: String = "",
    isSeen: Boolean = false
) {
    val isSending = sendStatus == BubbleMessageSendStatus.SENDING
    val isSender = type == BubbleMessageType.SENDER
    var isTimeVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (isSender) 64.dp else 16.dp,
                end = if (isSender) 16.dp else 64.dp
            ),
        horizontalAlignment = if (isSender) Alignment.End
        else Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSender) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = .08f)
                        )
                        .clickable { isTimeVisible = !isTimeVisible }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .align(
                            when (isSender) {
                                true -> Alignment.CenterEnd
                                else -> Alignment.CenterStart
                            }
                        )
                ) {
                    Text(text = message, style = MaterialTheme.typography.bodyMedium)
                }
            }
            AnimatedVisibility(visible = isSender && isSending) {
                Icon(
                    Lucide.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .32f)
                )
            }
        }
        AnimatedVisibility(visible = isTimeVisible) {
            Text(
                text = "${createdAt.localDateFormat()} • ${createdAt.localTimeFormat()}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = if (isSender) 0.dp else 12.dp,
                    end = if (isSender) 12.dp else 0.dp
                )
            )
        }

        /**
         * status jika pesan telah dilihat oleh other user sampai
         * check point message id ini
         */
        AnimatedVisibility(visible = isSender && isSeen && !isTimeVisible) {
            Text(
                text = "Telah dilihat",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(end = 12.dp, top = 4.dp)
            )
        }
    }
}