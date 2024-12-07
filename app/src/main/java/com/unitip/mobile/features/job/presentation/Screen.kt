package com.unitip.mobile.features.job.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Jobs")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text("ini adalah jobs screen hehe")
        }
    }
}