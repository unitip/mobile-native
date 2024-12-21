package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unitip.mobile.shared.presentation.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen() {
    val navController = LocalNavController.current

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.padding(bottom = 0.dp),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Red),
                title = {
                    Text("Jobs")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CreateJob) },
            ) {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }
        },
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(20) {
                JobItem(index = it)
            }
        }
    }
}

@Composable
private fun JobItem(index: Int) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(text = "$index. Rizal Dwi Anggoro")
            }
        }
    }
}