package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.User
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
    var isSelectRoleExpanded by remember { mutableStateOf(true) }

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
                AnimatedVisibility(visible = uiState.getDetail is State.GetDetail.Loading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
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
//            // app bar
//            CustomIconButton(
//                onClick = { navController.popBackStack() },
//                icon = Lucide.ChevronLeft,
//                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
//            )

            Text(
                text = "Berikut adalah beberapa peran yang Anda miliki dalam akun Unitip, masing-masing dengan akses dan tanggung jawab tertentu untuk menggunakan fitur di platform",
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .then(Modifier),
                style = MaterialTheme.typography.bodyMedium
            )

            with(uiState.getDetail) {
                when (this) {
                    is State.GetDetail.Success -> {
                        ListItem(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .clickable { isSelectRoleExpanded = !isSelectRoleExpanded },
                            headlineContent = { Text(text = "Peran saat ini") },
                            supportingContent = {
                                Text(
                                    text = RoleConstants.roles.find {
                                        it.role == selectedRole
                                    }?.title ?: "Tidak ditentukan"
                                )
                            },
                            leadingContent = {
                                Icon(Lucide.User, contentDescription = null)
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
                                            .clickable { }
                                            .padding(horizontal = 16.dp, vertical = 12.dp)
                                    ) {
                                        RadioButton(selected = true, onClick = null)
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

//            // loading indicator
//            AnimatedVisibility(visible = uiState.getDetail is State.GetDetail.Loading) {
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
//                    strokeCap = StrokeCap.Round
//                )
//            }
//            with(uiState.getDetail) {
//                when (this) {
//                    is State.GetDetail.Success -> LazyColumn(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 16.dp, end = 16.dp)
//                    ) {
//                        itemsIndexed(RoleConstants.roles.filter { it.role in this@with.roles }) { index, role ->
//                            val isSelected = selectedRole == role.role
//
//                            CustomCard(
//                                modifier = Modifier
//                                    .padding(top = if (index == 0) 16.dp else 8.dp)
//                                    .border(
//                                        width = 2.dp,
//                                        color = if (isSelected) MaterialTheme.colorScheme.onSurface.copy(
//                                            alpha = .32f
//                                        )
//                                        else Color.Unspecified,
//                                        shape = RoundedCornerShape(16.dp)
//                                    ),
//                                onClick = { selectedRole = role.role }
//                            ) {
//                                Row(
//                                    modifier = Modifier.padding(16.dp),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .size(48.dp)
//                                            .clip(RoundedCornerShape(12.dp))
//                                            .background(MaterialTheme.colorScheme.primaryContainer)
//                                    ) {
//                                        Icon(
//                                            role.icon,
//                                            contentDescription = null,
//                                            modifier = Modifier
//                                                .align(Alignment.Center)
//                                                .size(20.dp),
//                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
//                                        )
//                                    }
//                                    Column(modifier = Modifier.weight(1f)) {
//                                        Text(
//                                            text = role.title,
//                                            style = MaterialTheme.typography.titleMedium
//                                        )
//                                        Text(
//                                            text = role.subtitle,
//                                            style = MaterialTheme.typography.bodySmall
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    else -> Unit
//                }
//
//
//                AnimatedVisibility(visible = uiState.getDetail !is State.GetDetail.Loading) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//
//                        TextButton(
//                            onClick = { navController.popBackStack() },
//                            modifier = Modifier.fillMaxWidth()
//                        ) { Text(text = "Batal") }
//                    }
//                }
//            }
        }
    }
}