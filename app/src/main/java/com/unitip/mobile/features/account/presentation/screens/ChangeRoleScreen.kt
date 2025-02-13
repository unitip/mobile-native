package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.UserX
import com.unitip.mobile.features.account.presentation.viewmodels.ChangeRoleViewModel
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.constants.RoleConstants
import com.unitip.mobile.shared.presentation.viewmodels.SessionViewModel
import com.unitip.mobile.features.account.presentation.states.ChangeRoleState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeRoleScreen(
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: ChangeRoleViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }

    val session by sessionViewModel.session.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var selectedRole by remember { mutableStateOf(session.role) }
    var isSelectRoleExpanded by remember { mutableStateOf(false) }

    val getRoleIcon = remember {
        { role: String ->
            RoleConstants.roles.find { it.role == role }?.icon ?: Lucide.UserX
        }
    }
    val getRoleTitle = remember {
        { role: String ->
            RoleConstants.roles.find { it.role == role }?.title ?: "Tidak ditentukan"
        }
    }

    LaunchedEffect(uiState.changeDetail) {
        with(uiState.changeDetail) {
            if (this is State.ChangeDetail.Success)
                navController.navigate(HomeRoutes.Index) {
                    popUpTo<HomeRoutes.Index> { inclusive = true }
                }
        }
    }

    LaunchedEffect(uiState.getDetail) {
        with(uiState.getDetail) {
            when (this) {
                is State.GetDetail.Failure -> snackbarHostState.showSnackbar(
                    message = message
                )

                else -> Unit
            }
        }
        with(uiState.changeDetail) {
            when (this) {
                is State.ChangeDetail.Failure -> snackbarHostState.showSnackbar(
                    message = message
                )

                is State.ChangeDetail.Success -> navController.popBackStack()

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ubah Peran") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Lucide.ArrowLeft, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.getAllRoles() }) {
                            Icon(Lucide.RefreshCw, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.surfaceContainerHigh,
//                            MaterialTheme.colorScheme.surfaceContainerLowest,
//                        )
//                    )
//                )
                .padding(innerPadding)
        ) {
            with(uiState.getDetail) {
                when (this) {
                    is State.GetDetail.Loading -> Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            strokeCap = StrokeCap.Round
                        )
                    }

                    is State.GetDetail.Success -> {
                        Text(
                            text = "Berikut adalah beberapa peran yang Anda miliki dalam akun Unitip, masing-masing dengan akses dan tanggung jawab tertentu untuk menggunakan fitur di platform",
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                .then(Modifier),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        ListItem(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .clickable { isSelectRoleExpanded = !isSelectRoleExpanded },
                            headlineContent = { Text(text = "Peran saat ini") },
                            supportingContent = {
                                Text(
                                    text = when (selectedRole == session.role) {
                                        true -> getRoleTitle(selectedRole)
                                        else -> "${getRoleTitle(session.role)} -> " +
                                                getRoleTitle(selectedRole)
                                    }
                                )
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = getRoleIcon(selectedRole),
                                    contentDescription = null
                                )
                            },
                            trailingContent = {
                                Icon(
                                    if (isSelectRoleExpanded) Lucide.ChevronUp
                                    else Lucide.ChevronDown,
                                    contentDescription = null
                                )
                            }
                        )

                        AnimatedVisibility(visible = isSelectRoleExpanded) {
                            Column {
                                HorizontalDivider()
                                RoleConstants.roles.filter { it.role in roles }.map {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        modifier = Modifier
                                            .clickable {
                                                selectedRole = it.role
                                                isSelectRoleExpanded = false
                                            }
                                            .padding(horizontal = 16.dp, vertical = 12.dp)
                                    ) {
                                        RadioButton(
                                            selected = it.role == selectedRole,
                                            onClick = null
                                        )
                                        Column {
                                            Text(
                                                text = it.title,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                            Text(
                                                text = it.subtitle,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = ListItemDefaults.colors().supportingTextColor
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            enabled = selectedRole != session.role,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(16.dp),
                            onClick = { viewModel.changeRole(role = selectedRole) },
                        ) {
                            Text(text = "Simpan")
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}