package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.account.presentation.states.ChangeRoleState
import com.unitip.mobile.features.account.presentation.viewmodels.ChangeRoleViewModel
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.RoleConstants
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomIconButton

@Composable
fun ChangeRoleScreen(
    viewModel: ChangeRoleViewModel = hiltViewModel(),
) {

    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.uiState.collectAsState()
    var selectedRole by remember { mutableStateOf("") }

    LaunchedEffect(uiState.changeRoleDetail) {
        with(uiState.changeRoleDetail) {
            if (this is ChangeRoleState.ChangeRoleDetail.Success)
                navController.navigate(HomeRoutes.Index) {
                    popUpTo<HomeRoutes.Index> { inclusive = true }
                }
        }
    }

    LaunchedEffect(uiState.getRoleDetail) {
        with(uiState.getRoleDetail) {
            when (this) {
                is ChangeRoleState.GetRoleDetail.Failure -> snackbarHostState.showSnackbar(
                    message = message
                )

                else -> Unit
            }
        }
        with(uiState.changeRoleDetail) {
            when (this) {
                is ChangeRoleState.ChangeRoleDetail.Failure -> snackbarHostState.showSnackbar(
                    message = message
                )

                is ChangeRoleState.ChangeRoleDetail.Success -> navController.popBackStack()

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
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
                .padding(innerPadding)
        ) {
            // app bar
            CustomIconButton(
                onClick = { navController.popBackStack() },
                icon = Lucide.ChevronLeft,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(
                    text = "Pilih Peran",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Anda terdeteksi memiliki banyak peran di Unitip. " +
                            "Silahkan pilih peran terlebih dahulu sebelum lanjut menggunakan aplikasi",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // loading indicator
            AnimatedVisibility(visible = uiState.getRoleDetail is ChangeRoleState.GetRoleDetail.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    strokeCap = StrokeCap.Round
                )
            }
            with(uiState.getRoleDetail) {
                when (this) {
                    is ChangeRoleState.GetRoleDetail.Success -> LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        itemsIndexed(RoleConstants.roles.filter { it.role in this@with.roles }) { index, role ->
                            val isSelected = selectedRole == role.role

                            CustomCard(
                                modifier = Modifier
                                    .padding(top = if (index == 0) 16.dp else 8.dp)
                                    .border(
                                        width = 2.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = .32f
                                        )
                                        else Color.Unspecified,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                onClick = { selectedRole = role.role }
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Icon(
                                            role.icon,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(20.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = role.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = role.subtitle,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    else -> Unit
                }


                AnimatedVisibility(visible = uiState.getRoleDetail !is ChangeRoleState.GetRoleDetail.Loading) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(
                            onClick = {
                                viewModel.changeRole(role = selectedRole)
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
}