package com.unitip.mobile.features.setting.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.shared.presentation.viewmodels.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel = hiltViewModel(),
    onNavigate: (route: Any) -> Unit = {},
) {
    val session = sessionViewModel.session.collectAsState()

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
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = session.value?.name ?: "Tidak ada nama",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = session.value?.email ?: "Tidak ada alamat email",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alpha(.8f)
                    )
                    Text(
                        text = session.value?.token ?: "Tidak ada alamat email",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alpha(.8f)
                    )
                }
            }

            HorizontalDivider()
        }
    }
}