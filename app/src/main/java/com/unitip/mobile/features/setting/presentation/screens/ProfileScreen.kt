package com.unitip.mobile.features.setting.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.unitip.mobile.R
import com.unitip.mobile.features.setting.presentation.states.ProfileDetail
import com.unitip.mobile.features.setting.presentation.viewmodels.ProfileViewModel
import com.unitip.mobile.shared.presentation.components.ConfirmBottomSheet
import com.unitip.mobile.shared.presentation.components.LoadingBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigate: (route: Any) -> Unit = {},
    onLogout: () -> Unit = {},
    onUnauthorized: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var isConfirmLogoutSheetVisible by remember { mutableStateOf(false) }
    val confirmLogoutSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isLogoutSheetVisible by remember { mutableStateOf(false) }
    val logoutSheetState = rememberModalBottomSheetState(
        confirmValueChange = { false },
        skipPartiallyExpanded = true,
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is ProfileDetail.Loading -> {
                    isLogoutSheetVisible = true
                    logoutSheetState.show()
                }

                is ProfileDetail.Success -> {
                    launch { logoutSheetState.hide() }
                        .invokeOnCompletion {
                            isLogoutSheetVisible = false
                            onLogout()
                            viewModel.resetState()
                        }
                }

                is ProfileDetail.Failure -> {
                    launch { logoutSheetState.hide() }
                        .invokeOnCompletion {
                            isLogoutSheetVisible = false
                            if (code == 401) {
                                onUnauthorized()
                                return@invokeOnCompletion
                            }

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            viewModel.resetState()
                        }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text("Profile")
                }
            )
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
                modifier = Modifier.clickable {
                    scope.launch {
                        isConfirmLogoutSheetVisible = true
                        confirmLogoutSheetState.show()
                    }
                }
            )
        }
    }

    // bottom sheet loading
    if (isLogoutSheetVisible)
        LoadingBottomSheet(
            sheetState = logoutSheetState,
            image = painterResource(R.drawable.undraw_clean_up),
            title = "Mohon tunggu sebentar",
            subtitle = "Kami sedang memproses permintaan keluar Anda dari akun Unitip",
        )

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