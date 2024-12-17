package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

private data class Role(
    val title: String,
    val subtitle: String,
    val value: String,
    val icon: ImageVector,
)

private val defaultRoles = listOf(
    Role(
        title = "Driver",
        subtitle = "Ambil dan tawarkan pekerjaan kepada pengguna lainnya",
        value = "driver",
        icon = Lucide.Bike
    ),
    Role(
        title = "Customer",
        subtitle = "Buat pesanan dan ikut penawaran dari pengguna lainnya",
        value = "customer",
        icon = Lucide.User
    ),
)

@Composable
fun PickRoleScreen(
    roles: List<String>,
) {
    val availableRoles = defaultRoles.filter { it.value in roles }
    var selectedRole by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Text(
                text = "Pilih Peran",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Anda terdeteksi memiliki banyak peran di Unitip. " +
                        "Silahkan pilih peran terlebih dahulu sebelum lanjut menggunakan aplikasi",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .alpha(.8f)
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(availableRoles.size) { index ->
                    val role = availableRoles[index]
                    RoleListItem(
                        index = index,
                        isSelected = role.value === selectedRole,
                        onSelect = { selectedRole = it },
                        role = role,
                    )
                }
            }

            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Selesai")
            }
            TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Batal")
            }
        }
    }
}

@Composable
private fun RoleListItem(
    index: Int,
    isSelected: Boolean,
    onSelect: (value: String) -> Unit,
    role: Role,
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = when (isSelected) {
                true -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surface
            },
            contentColor = when (isSelected) {
                true -> MaterialTheme.colorScheme.onSecondaryContainer
                else -> MaterialTheme.colorScheme.onSurface
            },
        ),
        border = BorderStroke(
            1.dp, when (isSelected) {
                true -> MaterialTheme.colorScheme.secondary.copy(alpha = .16f)
                else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = .48f)
            }
        ),
        modifier = Modifier.padding(top = (if (index == 0) 32 else 8).dp),
        onClick = { onSelect(role.value) },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    role.icon,
                    contentDescription = role.title,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = role.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = role.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.alpha(.72f)
                )
            }
        }
    }
}