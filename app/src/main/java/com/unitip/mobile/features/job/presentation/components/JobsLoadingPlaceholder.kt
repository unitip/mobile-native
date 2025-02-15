package com.unitip.mobile.features.job.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun JobsLoadingPlaceholder() {
    val shimmerColor = MaterialTheme.colorScheme.outlineVariant
    val shimmerClip = RoundedCornerShape(4.dp)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(3) { index ->
            if (index > 0)
                HorizontalDivider(thickness = .56.dp)

            Column(modifier = Modifier.shimmer()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                            .background(shimmerColor)
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .height(MaterialTheme.typography.labelSmall.fontSize.value.dp)
                                    .background(shimmerColor)
                                    .weight(.5f)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .height(MaterialTheme.typography.labelSmall.fontSize.value.dp)
                                    .background(shimmerColor)
                                    .weight(.6f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Column(
                            modifier = Modifier.padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .fillMaxWidth()
                                    .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                                    .background(shimmerColor)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .fillMaxWidth()
                                    .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                                    .background(shimmerColor)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .fillMaxWidth(.64f)
                                    .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                                    .background(shimmerColor)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Array(4) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(shimmerClip)
                                            .size(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                                            .background(shimmerColor)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(shimmerClip)
                                            .fillMaxWidth(.32f)
                                            .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                                            .background(shimmerColor)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}