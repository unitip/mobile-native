package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.CalendarClock
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Circle
import com.composables.icons.lucide.CircleCheck
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.offer.commons.OfferConstans
import com.unitip.mobile.features.offer.presentation.components.CustomDatePickerDialog
import com.unitip.mobile.features.offer.presentation.components.CustomTimePickerDialog
import com.unitip.mobile.features.offer.presentation.components.ParticipantDropdown
import com.unitip.mobile.features.offer.presentation.viewmodels.CreateOfferViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.commons.extensions.appendTime
import com.unitip.mobile.shared.commons.extensions.localDateFormat
import com.unitip.mobile.shared.commons.extensions.localTimeFormat
import com.unitip.mobile.shared.commons.extensions.toIsoString
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomTextField
import com.unitip.mobile.features.offer.presentation.states.CreateOfferState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOfferScreen(
    viewModel: CreateOfferViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }
//    var expanded by remember { mutableStateOf(false) }
    // List jumlah peserta (1 sampai 5)
//    val participantsList = (1..5).toList()

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf(0) }
//    var type by rememberSaveable { mutableStateOf("") }
    var pickupArea by rememberSaveable { mutableStateOf("") }
    var destinationArea by rememberSaveable { mutableStateOf("") }
//    var availableUntil by rememberSaveable { mutableStateOf("") }
    var maxParticipants by rememberSaveable { mutableStateOf(1) }
    var isSelectType by rememberSaveable { mutableStateOf(false) }
    var selectType by rememberSaveable { mutableStateOf("") }

//    var dateTimeString by remember { mutableStateOf("") }
//    val context = LocalContext.current

    // Mendapatkan tanggal dan waktu saat ini
//    val currentDateTime = remember { LocalDateTime.now() }
//    var year by remember { mutableStateOf(currentDateTime.year) }
//    var month by remember { mutableStateOf(currentDateTime.monthValue - 1) } // Bulan dimulai dari 0
//    var day by remember { mutableStateOf(currentDateTime.dayOfMonth) }
//    var hour by remember { mutableStateOf(currentDateTime.hour) }
//    var minute by remember { mutableStateOf(currentDateTime.minute) }

    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var isTimePickerDialogVisible by remember { mutableStateOf(false) }
    var selectedDateTimeMillis by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is State.Detail.Failure -> {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                is State.Detail.Success -> {
                    // Kehalaman berikutnya
                    navController.popBackStack()

                    // Handle Succes
                    snackbarHostState.showSnackbar(
                        message = "Penawaran berhasil dibuat!",
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Short
                    )
                    // Reset State
                    viewModel.resetState()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Penawaran Baru") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Lucide.ArrowLeft,
                                contentDescription = null
                            )
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
                .padding(it)
                .imePadding()
                .verticalScroll(scrollState)
        ) {
            CustomCard(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                enabled = uiState.detail !is State.Detail.Loading
                            ) {
                                isSelectType = !isSelectType
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                Lucide.Bike,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Jenis Layanan",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = when (selectType.isBlank()) {
                                    true -> "Pilih jenis layanan"
                                    else -> OfferConstans.services.find { service ->
                                        service.value == selectType
                                    }!!.title
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Icon(
                            when (isSelectType) {
                                true -> Lucide.ChevronUp
                                else -> Lucide.ChevronDown
                            },
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = isSelectType
                    ) {
                        Column {
                            HorizontalDivider()

                            OfferConstans.services.map { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            isSelectType = false
                                            selectType = item.value
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Icon(
                                        when (item.value == selectType) {
                                            true -> Lucide.CircleCheck
                                            else -> Lucide.Circle
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                    }

                }
            }

            CustomTextField(
                label = "Judul Penawaran",
                value = title,
                onValueChange = { value -> title = value },
                placeholder = "Cth: Penawaran dimsum uma yumcha",
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            CustomTextField(
                label = "Deskripsi Penawaran",
                value = description,
                onValueChange = { value -> description = value },
                placeholder = "Cth: nanti nitipnya boleh 2 porsi per orang",
                enabled = uiState.detail !is State.Detail.Loading,
                minLines = 5,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            CustomTextField(
                label = "Harga Jasa Penawaran",
                value = if (price == 0) "" else price.toString(),
                onValueChange = { value ->
                    // Menghindari input selain angka
                    if (value.isEmpty() || value.all { char -> char.isDigit() }) {
                        price = value.toIntOrNull() ?: 0
                    }
                },
                placeholder = "Cth: 5000",
                enabled = uiState.detail !is State.Detail.Loading,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            CustomTextField(
                label = "Area Pengambilan Penawaran",
                value = pickupArea,
                onValueChange = { value -> pickupArea = value },
                placeholder = "Cth: Uma yumcha pasar Gede",
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            CustomTextField(
                label = "Area Pengantaran Penawaran",
                value = destinationArea,
                onValueChange = { value -> destinationArea = value },
                placeholder = "Cth: Sekitar UNS",
                enabled = uiState.detail !is State.Detail.Loading,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )


            // Dropdown menu
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded }
//            ) {
//                if (selectType == OfferConstans.services.find { it.value == "antar-jemput" }?.value) {
//                    // Automatically set maxParticipants to 1 if "antar jemput"
//                    maxParticipants = 1
//
//
//                    // Read-only OutlinedTextField
//                    OutlinedTextField(
//                        value = maxParticipants.toString(),  // Automatically displays 1
//                        onValueChange = {},  // No value change allowed
//                        label = { Text("Jumlah Peserta") },
//                        readOnly = true,  // Makes the field non-editable
//                        trailingIcon = {
//                            Icon(
//                                imageVector = Icons.Default.Lock,  // A lock icon to indicate it's read-only
//                                contentDescription = "Locked icon",
//                                modifier = Modifier.size(20.dp)
//                            )
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                } else {
//                    OutlinedTextField(
//                        value = maxParticipants.toString(),
//                        onValueChange = {},
//                        label = { Text("Jumlah Peserta") },
//                        readOnly = true,
//                        trailingIcon = {
//                            Icon(
//                                imageVector = Icons.Default.ArrowDropDown,
//                                contentDescription = "Dropdown icon",
//                                modifier = Modifier.size(20.dp)
//                            )
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .menuAnchor()
//                    )
//
//                    // Menu untuk memilih jumlah peserta
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        participantsList.forEach { participant ->
//                            DropdownMenuItem(
//                                text = { Text(participant.toString()) },
//                                onClick = {
//                                    maxParticipants = participant
//                                    expanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//            }

            ListItem(
                leadingContent = { Icon(Lucide.CalendarClock, contentDescription = null) },
                headlineContent = { Text(text = "Tersedia hingga") },
                supportingContent = {
                    Text(
                        text = when (selectedDateTimeMillis != null) {
                            true -> with(selectedDateTimeMillis!!.toIsoString()) {
                                "${this.localDateFormat()} ${this.localTimeFormat()}"
                            }

                            else -> "blom dipilih"
                        }
                    )
                },
                trailingContent = { Icon(Lucide.ChevronRight, contentDescription = null) },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        isDatePickerDialogVisible = true
                    }
            )

            ParticipantDropdown(
                maxValue = 5,
                value = maxParticipants,
                onValueChange = { value ->
                    maxParticipants = value
                },
                allowMultiple = selectType != "antar-jemput"
            )

            Button(
                enabled = uiState.detail !is State.Detail.Loading,
                onClick = {
                    if (selectType.isNotBlank() && selectedDateTimeMillis != null)
                        viewModel.create(
                            title = title,
                            description = description,
                            type = selectType,
                            availableUntil = selectedDateTimeMillis!!.toIsoString(),
                            price = price,
                            pickupArea = pickupArea,
                            destinationArea = destinationArea,
                            maxParticipants = maxParticipants
                        )
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Box {
                    Text(
                        "Selesai",
                        modifier = Modifier.alpha(
                            if (uiState.detail is State.Detail.Loading) 0f
                            else 1f
                        )
                    )
                    if (uiState.detail is State.Detail.Loading)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                            .align(Alignment.Center),
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }

    /**
     * date picker dialog untuk menentukan sampai kapan penawaran tersebut berlangsung
     */
    if (isDatePickerDialogVisible)
        CustomDatePickerDialog(
            onDismiss = { isDatePickerDialogVisible = false },
            onConfirm = {
                isDatePickerDialogVisible = false
                selectedDateTimeMillis = it

                // menampilkan time picker
                isTimePickerDialogVisible = true
            }
        )

    if (isTimePickerDialogVisible && selectedDateTimeMillis != null)
        CustomTimePickerDialog(
            onDismiss = { isTimePickerDialogVisible = false },
            onConfirm = {
                isTimePickerDialogVisible = false

                // meng-append time ke selected date sebelumnya
                selectedDateTimeMillis = selectedDateTimeMillis!!.appendTime(
                    hour = it.hour,
                    minute = it.minute
                )
            }
        )
}