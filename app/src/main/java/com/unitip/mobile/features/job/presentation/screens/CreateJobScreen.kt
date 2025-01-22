package com.unitip.mobile.features.job.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Square
import com.composables.icons.lucide.SquareCheck
import com.unitip.mobile.features.job.commons.JobConstants
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import com.unitip.mobile.features.job.presentation.viewmodels.CreateJobViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    viewModel: CreateJobViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val isImeVisible = WindowInsets.isImeVisible
    val listState = rememberLazyListState()
    val firstVisibleListItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var pickupLocation by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var isBottomSheetSelectServiceVisible by remember { mutableStateOf(false) }
    var isJoinAllowed by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.detail is CreateJobState.Detail.Loading

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is CreateJobState.Detail.Failure -> {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        contentWindowInsets = when (isImeVisible) {
//            true -> WindowInsets.statusBarsIgnoringVisibility
//            else -> ScaffoldDefaults.contentWindowInsets
//        }
    ) {
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
                    onClick = { navController.popBackStack() }
                )
                AnimatedVisibility(
                    visible = listState.canScrollBackward && firstVisibleListItemIndex.value > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(text = "Pekerjaan Baru", style = MaterialTheme.typography.titleLarge)
                }
            }

            AnimatedVisibility(visible = listState.canScrollBackward) {
                HorizontalDivider()
            }

            // loading bar
            AnimatedVisibility(visible = isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxWidth(),
                    strokeCap = StrokeCap.Round,
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .imePadding(),
                state = listState
            ) {
                item {
                    Text(
                        text = "Pekerjaan Baru",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
                item {
                    Text(
                        text = "Masukkan beberapa informasi berikut untuk membuat pekerjaan baru",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }

                item {
                    CustomCard(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        onClick = {
                            isBottomSheetSelectServiceVisible = true
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                Lucide.Bike,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Jenis Layanan",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Antar jemput",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = { Text("Judul") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors().copy(
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = .16f
                                )
                            ),
                        )

                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("Catatan tambahan") },
                            minLines = 5,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors().copy(
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = .16f
                                )
                            ),
                        )

                        OutlinedTextField(
                            value = pickupLocation,
                            onValueChange = { pickupLocation = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("Lokasi jemput") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors().copy(
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = .16f
                                )
                            ),
                        )

                        OutlinedTextField(
                            value = destination,
                            onValueChange = { destination = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("Destinasi") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors().copy(
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = .16f
                                )
                            ),
                        )
                    }
                }

                item {
                    CustomCard(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp
                        ),
                        onClick = {
                            isJoinAllowed = !isJoinAllowed
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                when (isJoinAllowed) {
                                    true -> Lucide.SquareCheck
                                    else -> Lucide.Square
                                },
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Izinkan orang lain bergabung dengan pekerjaan yang Anda buat. Proses pengantaran mungkin akan lebih lama karena driver harus mengantarkan ke setiap orang yang bergabung",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            // button create
            if (!isImeVisible) {
                Button(
                    enabled = !isLoading,
                    onClick = {
                        viewModel.create(
                            title = title,
                            note = note,
                            pickupLocation = pickupLocation,
                            destination = destination,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Selesai")
                }
            }
        }

        /**
         * bottom sheet untuk memilih jenis layanan berikut:
         * - antar jemput
         * - jasa titip
         */
        if (isBottomSheetSelectServiceVisible)
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { isBottomSheetSelectServiceVisible = false }
                },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = "Jenis Layanan",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Silahkan pilih janis layanan untuk pekerjaan yang akan Anda buat",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    JobConstants.services.mapIndexed { index, it ->
                        CustomCard(
                            modifier = Modifier.padding(
                                top = when (index == 0) {
                                    true -> 16.dp
                                    else -> 8.dp
                                }
                            ),
                            onClick = {

                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(Lucide.Square, contentDescription = null)
                                Text(text = it.title, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        onClick = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetSelectServiceVisible = false }
                        }
                    ) {
                        Text(text = "Batal")
                    }
                }
            }
    }
}