package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.KeyRound
import com.composables.icons.lucide.ListOrdered
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.icons.lucide.UserRoundPen
import com.composables.icons.lucide.UserX
import com.unitip.mobile.features.account.commons.AccountRoutes
import com.unitip.mobile.features.account.presentation.components.DialogLogout
import com.unitip.mobile.features.account.presentation.viewmodels.ProfileViewModel
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.features.account.presentation.states.ProfileState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val snackbarHost = remember { SnackbarHostState() }

    val scrollState = rememberScrollState()
    var isDialogLogoutVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val session = viewModel.session

    LaunchedEffect(uiState.logoutDetail) {
        with(uiState.logoutDetail) {
            when (this) {
                is State.LogoutDetail.Success -> {
                    isDialogLogoutVisible = false
                    navController.navigate(AuthRoutes.Index) {
                        popUpTo(HomeRoutes.Index) { inclusive = true }
                    }
                }

                is State.LogoutDetail.Failure -> {
                    isDialogLogoutVisible = false
                    snackbarHost.showSnackbar(message = message)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Akun Saya") }
                )
                HorizontalDivider()
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
    ) {
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
                .padding(it)
                .verticalScroll(scrollState)
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

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = session.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = session.email,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alpha(.8f)
                    )
                    Text(
                        text = session.token,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alpha(.8f)
                    )
                    Text(
                        text = session.role,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            HorizontalDivider(thickness = 0.56.dp)

            Text(
                text = "Akun",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            ListItem(
                modifier = Modifier.clickable { navController.navigate(AccountRoutes.UpdateProfile) },
                leadingContent = { Icon(Lucide.User, contentDescription = null) },
                headlineContent = { Text(text = "Ubah Informasi Akun") }
            )
            ListItem(
                modifier = Modifier.clickable { navController.navigate(AccountRoutes.UpdatePassword) },
                leadingContent = { Icon(Lucide.KeyRound, contentDescription = null) },
                headlineContent = { Text(text = "Ubah Kata Sandi") }
            )
            ListItem(
                modifier = Modifier.clickable { navController.navigate(AccountRoutes.ChangeRole) },
                leadingContent = { Icon(Lucide.UserRoundPen, contentDescription = null) },
                headlineContent = { Text(text = "Ubah Peran") }
            )
            ListItem(
                modifier = Modifier.clickable { },
                leadingContent = { Icon(Lucide.UserX, contentDescription = null) },
                headlineContent = { Text(text = "Hapus Akun") }
            )

            Text(
                text = "Pesanan",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            ListItem(
                modifier = Modifier.clickable { navController.navigate(AccountRoutes.OrderHistory) },
                leadingContent = { Icon(Lucide.ListOrdered, contentDescription = null) },
                headlineContent = { Text(text = "Riwayat Pesanan") }
            )

            Text(
                text = "Lainnya",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            ListItem(
                modifier = Modifier.clickable { isDialogLogoutVisible = true },
                leadingContent = { Icon(Lucide.LogOut, contentDescription = null) },
                headlineContent = { Text(text = "Keluar") }
            )
        }
    }

    if (isDialogLogoutVisible)
        DialogLogout(
            isLoading = uiState.logoutDetail is State.LogoutDetail.Loading,
            onConfirm = {
                viewModel.logout()
            },
            onDismiss = {
                isDialogLogoutVisible = false
            }
        )
}