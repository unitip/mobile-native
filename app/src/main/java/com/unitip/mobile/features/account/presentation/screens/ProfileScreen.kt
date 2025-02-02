package com.unitip.mobile.features.account.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.icons.lucide.UserRoundPen
import com.unitip.mobile.R
import com.unitip.mobile.features.account.commons.AccountRoutes
import com.unitip.mobile.features.account.presentation.states.ProfileState
import com.unitip.mobile.features.account.presentation.viewmodels.ProfileViewModel
import com.unitip.mobile.features.auth.commons.AuthRoutes
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.GetPopResult
import com.unitip.mobile.shared.commons.extensions.redirectToUnauthorized
import com.unitip.mobile.shared.presentation.components.ConfirmBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }

    var isConfirmLogoutSheetVisible by remember { mutableStateOf(false) }
    val confirmLogoutSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val uiState by viewModel.uiState.collectAsState()

    navController.GetPopResult<Boolean>(key = "updateProfile") {
        Log.d("ProfileScreen", "ProfileScreen: $it")
    }

    LaunchedEffect(uiState.logoutDetail) {
        with(uiState.logoutDetail) {
            when (this) {
                is ProfileState.LogoutDetail.Success -> {
                    navController.navigate(AuthRoutes.Index) {
                        popUpTo(HomeRoutes.Index) { inclusive = true }
                    }
                }

                is ProfileState.LogoutDetail.Failure -> {
                    when (code) {
                        401 -> navController.redirectToUnauthorized()
                        else -> {
                            snackbarHost.showSnackbar(
                                message = message,
                                actionLabel = "Oke",
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
    ) {
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
                .padding(it)
        ) {
            Text(
                text = "Akun Saya",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            LazyColumn {
                item {
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
                        with(uiState.session) {
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
                                Text(
                                    text = role,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .clip(RoundedCornerShape(32.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Button(modifier = Modifier.padding(top = 16.dp),

                                    onClick = { navController.navigate(AccountRoutes.EditProfile) }) {
                                    Text(
                                        text = "Edit Profil",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Button(modifier = Modifier.padding(top = 16.dp),

                                    onClick = { navController.navigate(AccountRoutes.EditPassword) }) {
                                    Text(
                                        text = "Edit Password",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    with(uiState.session) {
                        Column() {
                            HorizontalDivider()
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = "Nama Pengguna",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            HorizontalDivider()

                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = "Email",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = email,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            HorizontalDivider()
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = "Peran",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = role,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            HorizontalDivider()

                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = "Jenis Kelamin",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = gender,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = .08f))
                            .clickable {


                            }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                Lucide.UserRoundPen,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(text = "Ganti Peran", style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                }
                item {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = .08f))
                            .clickable {
                                scope.launch {
                                    isConfirmLogoutSheetVisible = true
                                    confirmLogoutSheetState.show()
                                }
                            }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                Lucide.LogOut,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(text = "Keluar", style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                }
            }


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