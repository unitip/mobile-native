package com.unitip.mobile.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun DashboardDriverApplicationsLoadingPlaceholder() {
    val shimmerColor = MaterialTheme.colorScheme.outlineVariant
    val shimmerClip = RoundedCornerShape(4.dp)

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .shimmer()
    ) {
        Array(3) {
            if (it > 0)
                HorizontalDivider(thickness = .56.dp)

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(shimmerClip)
                            .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                            .weight(.6f)
                            .background(shimmerColor)
                    )
                    Box(
                        modifier = Modifier
                            .clip(shimmerClip)
                            .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                            .weight(.4f)
                            .background(shimmerColor)
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
                            .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                            .fillMaxWidth(1f)
                            .background(shimmerColor)
                    )
                    Box(
                        modifier = Modifier
                            .clip(shimmerClip)
                            .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                            .fillMaxWidth(.56f)
                            .background(shimmerColor)
                    )
                }

                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Array(2) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .size(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                                    .background(shimmerColor)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(shimmerClip)
                                    .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                                    .fillMaxWidth(if (it == 0) .5f else .4f)
                                    .background(shimmerColor)
                            )
                        }
                        if (it == 0)
                            Box(
                                modifier = Modifier
                                    .padding(start = MaterialTheme.typography.bodySmall.fontSize.value.dp + 8.dp)
                                    .clip(shimmerClip)
                                    .height(MaterialTheme.typography.bodySmall.fontSize.value.dp)
                                    .fillMaxWidth(.84f)
                                    .background(shimmerColor)
                            )
                    }
                }
            }
        }
    }
}