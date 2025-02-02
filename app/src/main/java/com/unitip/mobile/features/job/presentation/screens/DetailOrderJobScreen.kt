package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.job.presentation.states.DetailOrderJobState
import com.unitip.mobile.features.job.presentation.viewmodels.DetailOrderJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController

@Composable
fun DetailOrderJobScreen(
    orderId: String,
    viewModel: DetailOrderJobViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    with(uiState.cancelDetail) {
        when (this) {
            is DetailOrderJobState.CancelDetail.Success -> {
                Toast.makeText(context, "berhasil batal!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }

            is DetailOrderJobState.CancelDetail.Failure -> Toast.makeText(
                context,
                "gagal batal: ${this.message}",
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "ini halaman untuk menampilkan detail order job yang dilakukan oleh customer")
            Text(text = "id ordernya: $orderId")
            with(uiState.cancelDetail) {
                when (this) {
                    is DetailOrderJobState.CancelDetail.Loading -> CircularProgressIndicator()
                    else -> Button(
                        onClick = {
                            viewModel.cancel()
                        }
                    ) {
                        Text(text = "Batalin, aku capek")
                    }
                }
            }
            Button(onClick = {}) {
                Text(text = "Chat customer")
            }
            Button(onClick = {}) {
                Text(text = "Tandai selesai")
            }
        }
    }
}