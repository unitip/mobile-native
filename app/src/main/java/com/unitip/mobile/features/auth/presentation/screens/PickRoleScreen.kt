package com.unitip.mobile.features.auth.presentation.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.auth.commons.AuthConstants
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.auth.domain.models.Role
import com.unitip.mobile.features.auth.presentation.states.PickRoleState
import com.unitip.mobile.features.auth.presentation.viewmodels.PickRoleViewModel
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@Composable
fun PickRoleScreen(
    viewModel: PickRoleViewModel = hiltViewModel(),
    email: String,
    password: String,
    roles: List<String>,
) {
    val availableRoles = AuthConstants.roles.filter { it.role in roles }
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.uiState.collectAsState()
    var selectedRole by remember { mutableStateOf("") }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is PickRoleState.Detail.Failure -> snackbarHostState.showSnackbar(
                    message = message
                )

                is PickRoleState.Detail.Success -> navController.navigate(HomeRoutes.Index) {
                    popUpTo(AuthRoutes.Index) { inclusive = true }
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = uiState.detail is PickRoleState.Detail.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    strokeCap = StrokeCap.Round
                )
            }

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
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
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                itemsIndexed(availableRoles) { index, role ->
                    RoleListItem(
                        index = index,
                        isSelected = role.role === selectedRole,
                        onSelect = { selectedRole = it },
                        role = role,
                    )
                }
            }

            AnimatedVisibility(visible = uiState.detail !is PickRoleState.Detail.Loading) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = {
                            if (selectedRole.isNotEmpty())
                                viewModel.login(
                                    email = email,
                                    password = password,
                                    role = selectedRole
                                )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(text = "Selesai") }
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(text = "Batal") }
                }
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
        onClick = { onSelect(role.role) },
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