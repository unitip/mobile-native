package com.unitip.mobile.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unitip.mobile.shared.presentation.ui.theme.UnitipTheme

private enum class ButtonType {
    Default,
    Outlined,
    Text, 
}

@Composable
private fun BaseButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    type: ButtonType = ButtonType.Default,
) {
    val backgroundColors = mapOf(
        ButtonType.Default to MaterialTheme.colorScheme.primary,
        ButtonType.Outlined to MaterialTheme.colorScheme.surface,
        ButtonType.Text to MaterialTheme.colorScheme.surface,
    )
    val foregroundColors = mapOf(
        ButtonType.Default to MaterialTheme.colorScheme.onPrimary,
        ButtonType.Outlined to MaterialTheme.colorScheme.primary,
        ButtonType.Text to MaterialTheme.colorScheme.primary,
    )

    var mergedModifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .alpha(if (enabled) 1f else .48f)
        .background(color = backgroundColors[type]!!)
        .fillMaxWidth()

    if (type == ButtonType.Outlined)
        mergedModifier = mergedModifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
            shape = RoundedCornerShape(8.dp)
        )

    if (enabled)
        mergedModifier = mergedModifier.clickable { onClick() }

    Box(modifier = mergedModifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = foregroundColors[type]!!,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
        )
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    BaseButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        type = ButtonType.Default
    )
}

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    BaseButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        type = ButtonType.Outlined
    )
}

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    BaseButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        type = ButtonType.Text
    )
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    UnitipTheme {
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                CustomButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    text = "Default Button"
                )
                CustomButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Disabled Default Button",
                    enabled = false
                )

                CustomOutlinedButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Outlined Button"
                )
                CustomOutlinedButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Disabled Outlined Button",
                    enabled = false,
                )

                CustomTextButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Text Button"
                )
                CustomTextButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Disabled Text Button",
                    enabled = false,
                )
            }
        }
    }
}