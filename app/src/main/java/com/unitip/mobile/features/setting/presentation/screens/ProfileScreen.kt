package com.unitip.mobile.features.setting.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.unitip.mobile.R
import com.unitip.mobile.features.auth.utils.AuthRoutes
import com.unitip.mobile.features.home.core.HomeRoutes
import com.unitip.mobile.features.setting.presentation.states.ProfileStateDetail
import com.unitip.mobile.features.setting.presentation.viewmodels.ProfileViewModel
import com.unitip.mobile.shared.presentation.components.ConfirmBottomSheet
import com.unitip.mobile.shared.presentation.compositional.LocalNavController
import com.unitip.mobile.shared.utils.extensions.redirectToUnauthorized
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }

    var isConfirmLogoutSheetVisible by remember { mutableStateOf(false) }
    val confirmLogoutSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is ProfileStateDetail.Success -> {
                    navController.navigate(AuthRoutes.Index) {
                        popUpTo(HomeRoutes.Index) { inclusive = true }
                    }
                }

                is ProfileStateDetail.Failure -> {
                    when (code) {
                        401 -> navController.redirectToUnauthorized()
                        else -> {
                            snackbarHost.showSnackbar(
                                message = message,
                                actionLabel = "Oke",
                                duration = SnackbarDuration.Indefinite
                            )
                            viewModel.resetState()
                        }
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Text("Profile")
                    }
                )
                AnimatedVisibility(visible = uiState.detail is ProfileStateDetail.Loading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(state = scrollState)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        Lucide.User,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                with(uiState) {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.alpha(.8f)
                        )
                        Text(
                            text = token,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.alpha(.8f)
                        )
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ) {
                            Text(text = "customer")
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            ListItem(
                leadingContent = {
                    Icon(
                        Lucide.LogOut,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                headlineContent = { Text(text = "Keluar") },
                modifier = Modifier.clickable(
                    enabled = uiState.detail !is ProfileStateDetail.Loading
                ) {
                    scope.launch {
                        isConfirmLogoutSheetVisible = true
                        confirmLogoutSheetState.show()
                    }
                }
            )
        }
    }

    if (isConfirmLogoutSheetVisible)
        ConfirmBottomSheet(
            sheetState = confirmLogoutSheetState,
            image = painterResource(R.drawable.undraw_questions),
            title = "Keluar",
            subtitle = "Apakah Anda yakin akan keluar dari akun Unitip?",
            negativeText = "Batal",
            positiveText = "Keluar",
            onNegative = {
                scope.launch {
                    confirmLogoutSheetState.hide()
                }.invokeOnCompletion {
                    isConfirmLogoutSheetVisible = false
                }
            },
            onPositive = {
                scope.launch {
                    confirmLogoutSheetState.hide()
                }.invokeOnCompletion {
                    isConfirmLogoutSheetVisible = false
                    viewModel.logout()
                }
            }
        )
}