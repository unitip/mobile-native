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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import com.unitip.mobile.features.job.presentation.viewmodels.ApplyJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField

@Composable
fun ApplyJobScreen(
    viewModel: ApplyJobViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    var bidPrice by remember { mutableIntStateOf(0) }
    var bidNote by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    with(uiState.detail) {
        if (this is ApplyJobState.Detail.Success) {
            Toast.makeText(context, "berhasil mengirimkan lamaran anda!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }

        if (this is ApplyJobState.Detail.Failure)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "ini adalah halaman untuk apply job")
            Text(text = "harga harapan dari customer adalah Rp ... (blom implementasi)")

            CustomTextField(
                label = "Harga Penawaran",
                value = bidPrice.toString(),
                onValueChange = { value ->
                    bidPrice = value.toIntOrNull() ?: 0
                }
            )

            CustomTextField(
                label = "Catatan Penawaran",
                value = bidNote,
                onValueChange = { value ->
                    bidNote = value
                },
                minLines = 5,
            )
            Text(text = "berikan alasan yang jelas mengapa anda menawarkan harga tersebut alias tidak sesuai dengan ekspektasi customer")

            with(uiState.detail) {
                if (this is ApplyJobState.Detail.Loading)
                    CircularProgressIndicator()
                else
                    Button(
                        onClick = {
                            viewModel.apply(
                                price = bidPrice,
                                bidNote = bidNote
                            )
                        }
                    ) {
                        Text(text = "Kirim lamaran")
                    }
            }
        }
    }
}