package com.unitip.mobile.features.chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

enum class BubbleMessageType {
    SENDER,
    RECEIVER
}

@Composable
fun BubbleMessage(
    modifier: Modifier = Modifier,
    type: BubbleMessageType = BubbleMessageType.SENDER,
    message: String = ""
) {
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
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isSender) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = .08f)
                )
                .clickable { isTimeVisible = !isTimeVisible }
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
        AnimatedVisibility(visible = isTimeVisible) {
            Text(
                text = "10.10",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = if (isSender) 0.dp else 12.dp,
                    end = if (isSender) 12.dp else 0.dp
                )
            )
        }
    }
}