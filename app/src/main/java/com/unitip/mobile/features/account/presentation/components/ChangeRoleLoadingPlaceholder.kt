package com.unitip.mobile.features.account.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ChangeRoleLoadingPlaceholder() {
    val shimmerColor = MaterialTheme.colorScheme.outlineVariant
    val shimmerClip = RoundedCornerShape(4.dp)

    Column(
        modifier = Modifier
            .shimmer()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(shimmerClip)
                    .background(shimmerColor)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .height(MaterialTheme.typography.bodyLarge.lineHeight.value.dp)
                        .clip(shimmerClip)
                        .background(shimmerColor)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .height(MaterialTheme.typography.bodyMedium.lineHeight.value.dp)
                        .clip(shimmerClip)
                        .background(shimmerColor)
                )
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(shimmerClip)
                    .background(shimmerColor)
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
                .clip(CircleShape)
                .background(shimmerColor)
                .height(ButtonDefaults.MinHeight)
                .fillMaxWidth(.28f)
        )
    }
}