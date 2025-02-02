package com.unitip.mobile.features.setting.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.setting.presentation.states.EditPasswordState
import com.unitip.mobile.features.setting.presentation.viewmodels.EditPasswordViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import com.unitip.mobile.shared.presentation.components.CustomTextField

@Composable
fun EditPasswordScreen(
    viewModel: EditPasswordViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val uiState by viewModel.uiState.collectAsState()

    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(uiState.editDetail) {
        with(uiState.editDetail) {
            when (this) {
                is EditPasswordState.EditDetail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Scaffold(contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top)) {
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
            // app bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                CustomIconButton(
                    icon = Lucide.ChevronLeft,
                    onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "updateProfile",
                            true
                        )
                        navController.popBackStack()
                    }
                )
                AnimatedVisibility(
                    visible = listState.canScrollBackward,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(text = "Edit Profil", style = MaterialTheme.typography.titleLarge)
                }

                AnimatedVisibility(
                    visible = listState.canScrollBackward &&
                            uiState.editDetail !is EditPasswordState.EditDetail.Loading
                ) {
                    HorizontalDivider()
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .imePadding(),
                state = listState
            ) {
                item {
                    Text(
                        text = "Edit Password",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = when (uiState.editDetail is EditPasswordState.EditDetail.Loading) {
                                true -> 16.dp
                                else -> 0.dp
                            }
                        )
                    )
                }
                item {
                    Text(
                        text = "Masukkan beberapa informasi yang ingin Anda ubah",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
                item {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {

                        CustomTextField(
                            label = "Password Baru",
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            enabled = uiState.editDetail !is EditPasswordState.EditDetail.Loading
                        )
                        CustomTextField(
                            label = "Konfirmasi Password",
                            value = confirmNewPassword,
                            onValueChange = { confirmNewPassword = it },
                            enabled = uiState.editDetail !is EditPasswordState.EditDetail.Loading
                        )
                        Button(modifier = Modifier.padding(top = 16.dp),
                            onClick = {
                                viewModel.edit(
                                    password = newPassword,
                                    confirmPassword = confirmNewPassword
                                )
                            }) { Text("Simpan Perubahan") }
                    }
                    
                }
            }
        }
    }
}