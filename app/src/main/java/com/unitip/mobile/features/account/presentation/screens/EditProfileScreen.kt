package com.unitip.mobile.features.account.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.account.presentation.viewmodels.EditProfileViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.constants.GenderConstant
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.shared.presentation.viewmodels.SessionViewModel
import com.unitip.mobile.features.account.presentation.states.EditProfileState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val genderOptions = listOf(
        mapOf("label" to "Laki-laki", "value" to GenderConstant.Male),
        mapOf("label" to "Perempuan", "value" to GenderConstant.Female),
        mapOf("label" to "Tidak ditentukan", "value" to GenderConstant.NotSpecified),
    )

    val navController = LocalNavController.current

    val session by sessionViewModel.session.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var name by rememberSaveable { mutableStateOf(session.name) }
    var gender by rememberSaveable { mutableStateOf(session.gender) }
    var isSelectGenderExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Success -> {
                    sessionViewModel.refreshSession()
                    navController.popBackStack()
                }

                is State.Detail.Failure -> snackbarHostState.showSnackbar(message = message)

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
            CustomTextField(
                label = "Nama Pengguna",
                value = name,
                onValueChange = { name = it },
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            ListItem(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable(enabled = uiState.detail !is State.Detail.Loading) {
                        isSelectGenderExpanded = !isSelectGenderExpanded
                    },
                headlineContent = { Text(text = "Jenis Kelamin") },
                supportingContent = {
                    Text(
                        text = genderOptions.find { item ->
                            item["value"] == gender
                        }!!["label"] as String
                    )
                },
                trailingContent = {
                    Icon(
                        if (isSelectGenderExpanded) Lucide.ChevronUp
                        else Lucide.ChevronDown,
                        contentDescription = null
                    )
                }
            )
            AnimatedVisibility(visible = isSelectGenderExpanded) {
                HorizontalDivider()
                Column {
                    genderOptions.map {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    gender = it["value"] as GenderConstant
                                    isSelectGenderExpanded = false
                                }
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing)
                        ) {
                            RadioButton(
                                selected = it["value"] == gender,
                                onClick = null
                            )
                            Text(text = it["label"] as String)
                        }
                    }
                }
            }


            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End),
                onClick = {
                    viewModel.save(
                        name = name,
                        gender = gender,
                    )
                },
                enabled = uiState.detail !is State.Detail.Loading
            ) {
                Box {
                    Text(
                        "Simpan Perubahan",
                        modifier = Modifier.alpha(if (uiState.detail is State.Detail.Loading) 0f else 1f)
                    )
                    if (uiState.detail is State.Detail.Loading)
                        CircularProgressIndicator(
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(ButtonDefaults.IconSize)
                                .align(Alignment.Center)
                        )
                }
            }
        }
    }
}