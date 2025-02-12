package com.unitip.mobile.features.account.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.account.presentation.viewmodels.EditProfileViewModel
import com.unitip.mobile.features.home.commons.HomeRoutes
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.features.account.presentation.states.EditProfileState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
//    val listState = rememberLazyListState()

    val uiState by viewModel.uiState.collectAsState()

    var name by rememberSaveable { mutableStateOf(uiState.session.name) }
    var gender by rememberSaveable { mutableStateOf(uiState.session.gender) }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Failure -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            if (this is State.Detail.Success)
                navController.navigate(HomeRoutes.Index) {
                    popUpTo<HomeRoutes.Index> { inclusive = true }
                }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Ubah Informasi Akun") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Lucide.ArrowLeft, contentDescription = null)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
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
        ) {
            // app bar
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.padding(16.dp)
//            ) {
//                CustomIconButton(
//                    icon = Lucide.ChevronLeft,
//                    onClick = {
//                        navController.previousBackStackEntry?.savedStateHandle?.set(
//                            "updateProfile",
//                            true
//                        )
//                        navController.popBackStack()
//                    }
//                )
//                AnimatedVisibility(
//                    visible = listState.canScrollBackward,
//                    enter = fadeIn(),
//                    exit = fadeOut()
//                ) {
//                    Text(text = "Edit Profil", style = MaterialTheme.typography.titleLarge)
//                }
//
//                AnimatedVisibility(
//                    visible = listState.canScrollBackward &&
//                            uiState.editDetail !is EditProfileState.EditDetail.Loading
//                ) {
//                    HorizontalDivider()
//                }
//            }

            CustomTextField(
                label = "Nama Pengguna",
                value = name,
                onValueChange = { name = it },
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
            CustomTextField(
                label = "Jenis Kelamin",
                value = gender,
                onValueChange = { gender = it },
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End),
                onClick = {
                    viewModel.edit(
                        name = name,
                        gender = gender,
                    )
                }
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}