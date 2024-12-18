package com.unitip.mobile.features.setting.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.setting.presentation.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigate: (route: Any) -> Unit = {},
) {
    var isDialogConfirmLogoutVisible by remember { mutableStateOf(false) }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Profile")
                }
            )
        }
    ) {
        val scrollState = rememberScrollState()

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
                        imageVector = Icons.TwoTone.Person,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                with(uiState.value) {
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
                    isDialogConfirmLogoutVisible = true
                }
            )
        }

        // alert dialog confirm logout
        if (isDialogConfirmLogoutVisible)
            AlertDialog(
                onDismissRequest = { isDialogConfirmLogoutVisible = false },
                title = { Text("Keluar") },
                text = { Text("Apakah Anda yakin akan keluar dari akun Unitip?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                        }
                    ) {
                        Text("Keluar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { isDialogConfirmLogoutVisible = false }
                    ) {
                        Text("Batal")
                    }
                }
            )
    }
}