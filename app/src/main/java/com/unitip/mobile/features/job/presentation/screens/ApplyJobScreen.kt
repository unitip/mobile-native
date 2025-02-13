package com.unitip.mobile.features.job.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import com.unitip.mobile.features.job.presentation.viewmodels.ApplyJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Kirim Penawaran") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Lucide.ArrowLeft,
                                contentDescription = null
                            )
                        }
                    })
                HorizontalDivider()
            }
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(16.dp)) {
            Text(
                text = "Berikan alasan yang jelas mengapa Anda menawarkan harga yang lebih tinggi dari ekspektasi pelanggan agar mereka memahami nilai tambah yang Anda berikan dan tetap tertarik dengan penawaran Anda",
                style = MaterialTheme.typography.bodyMedium
            )
//            Text(text = "ini adalah halaman untuk apply job")
//            Text(text = "harga harapan dari customer adalah Rp ... (blom implementasi)")

            CustomTextField(
                modifier = Modifier.padding(top = 16.dp),
                label = "Harga Penawaran",
                value = bidPrice.toString(),
                onValueChange = { value ->
                    bidPrice = value.toIntOrNull() ?: 0
                }
            )

            CustomTextField(
                modifier = Modifier.padding(top = 8.dp),
                label = "Catatan Penawaran",
                value = bidNote,
                onValueChange = { value ->
                    bidNote = value
                },
                minLines = 5,
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End),
                onClick = {
                    viewModel.apply(
                        price = bidPrice,
                        bidNote = bidNote
                    )
                }
            ) {
                Text(text = "Kirim Lamaran")
            }
        }
    }
}