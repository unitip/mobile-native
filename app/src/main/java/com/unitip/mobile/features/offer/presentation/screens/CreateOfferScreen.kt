package com.unitip.mobile.features.offer.presentation.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Circle
import com.composables.icons.lucide.CircleCheck
import com.composables.icons.lucide.Lucide
import com.unitip.mobile.features.offer.commons.OfferConstans
import com.unitip.mobile.features.offer.presentation.states.CreateOfferState
import com.unitip.mobile.features.offer.presentation.viewmodels.CreateOfferViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import com.unitip.mobile.shared.presentation.components.CustomTextField
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateOfferScreen(
    viewModel: CreateOfferViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val isImeVisible = WindowInsets.isImeVisible
    val listState = rememberLazyListState()
    val firstVisibleListItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    var expanded by remember { mutableStateOf(false) }
    // List jumlah peserta (1 sampai 5)
    val participantsList = (1..5).toList()


    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf(0) }
    var type by rememberSaveable { mutableStateOf("") }
    var pickupArea by rememberSaveable { mutableStateOf("") }
    var destinationArea by rememberSaveable { mutableStateOf("") }
    var availableUntil by rememberSaveable { mutableStateOf("") }
    var maxParticipants by rememberSaveable { mutableStateOf(1) }
    var isSelectType by rememberSaveable { mutableStateOf(false) }
    var selectType by rememberSaveable { mutableStateOf("") }

    var dateTimeString by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Mendapatkan tanggal dan waktu saat ini
    val currentDateTime = remember { LocalDateTime.now() }
    var year by remember { mutableStateOf(currentDateTime.year) }
    var month by remember { mutableStateOf(currentDateTime.monthValue - 1) } // Bulan dimulai dari 0
    var day by remember { mutableStateOf(currentDateTime.dayOfMonth) }
    var hour by remember { mutableStateOf(currentDateTime.hour) }
    var minute by remember { mutableStateOf(currentDateTime.minute) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is CreateOfferState.Detail.Failure -> {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "Oke",
                        duration = SnackbarDuration.Indefinite
                    )
                    viewModel.resetState()
                }

                is CreateOfferState.Detail.Success -> {
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


                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                    Text(text = "Penawaran Baru", style = MaterialTheme.typography.titleLarge)
                }
            }
            AnimatedVisibility(
                visible = listState.canScrollBackward &&
                        uiState.detail !is CreateOfferState.Detail.Loading
            ) {
                HorizontalDivider()
            }

            // loading bar
            AnimatedVisibility(visible = uiState.detail is CreateOfferState.Detail.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    strokeCap = StrokeCap.Round
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
                        text = "Penawaran Baru",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = when (uiState.detail is CreateOfferState.Detail.Loading) {
                                true -> 16.dp
                                else -> 0.dp
                            }
                        )
                    )
                }
                item {
                    Text(
                        text = "Masukkan beberapa informasi berikut untuk membuat penawaran baru",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }

                item {
                    CustomCard(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = uiState.detail !is CreateOfferState.Detail.Loading
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
                }
                /**
                 * form untuk membuat pekerjaan baru
                 */
                item {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        CustomTextField(
                            label = "Judul penawaran",
                            value = title,
                            onValueChange = { title = it },
                            placeholder = "Cth: Penawaran dimsum uma yumcha",
                            enabled = uiState.detail !is CreateOfferState.Detail.Loading
                        )

                        CustomTextField(
                            label = "Deskripsi penawaran",
                            value = description,
                            onValueChange = { description = it },
                            placeholder = "Cth: nanti nitipnya boleh 2 porsi per orang",
                            enabled = uiState.detail !is CreateOfferState.Detail.Loading
                        )

                        CustomTextField(
                            label = "Harga penawaran",
                            value = if (price == 0) "" else price.toString(),
                            onValueChange = {
                                // Menghindari input selain angka
                                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                                    price = it.toIntOrNull() ?: 0
                                }
                            },
                            placeholder = "Cth: 5000",
                            enabled = uiState.detail !is CreateOfferState.Detail.Loading,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        CustomTextField(
                            label = "Area pengambilan penawaran",
                            value = pickupArea,
                            onValueChange = { pickupArea = it },
                            placeholder = "Cth: Uma yumcha pasar Gede",
                            enabled = uiState.detail !is CreateOfferState.Detail.Loading
                        )

                        CustomTextField(
                            label = "Area pengantaran penawaran",
                            value = destinationArea,
                            onValueChange = { destinationArea = it },
                            placeholder = "Cth: Sekitar UNS",
                            enabled = uiState.detail !is CreateOfferState.Detail.Loading
                        )

                        // Dropdown menu
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            if (selectType == OfferConstans.services.find { it.value == "antar-jemput" }?.value) {
                                // Automatically set maxParticipants to 1 if "antar jemput"
                                maxParticipants = 1


                                // Read-only OutlinedTextField
                                OutlinedTextField(
                                    value = maxParticipants.toString(),  // Automatically displays 1
                                    onValueChange = {},  // No value change allowed
                                    label = { Text("Jumlah Peserta") },
                                    readOnly = true,  // Makes the field non-editable
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Lock,  // A lock icon to indicate it's read-only
                                            contentDescription = "Locked icon",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                            } else {
                                OutlinedTextField(
                                    value = maxParticipants.toString(),
                                    onValueChange = {},
                                    label = { Text("Jumlah Peserta") },
                                    readOnly = true,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown icon",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                )

                                // Menu untuk memilih jumlah peserta
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    participantsList.forEach { participant ->
                                        DropdownMenuItem(
                                            text = { Text(participant.toString()) },
                                            onClick = {
                                                maxParticipants = participant
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            // Tampilkan teks yang menunjukkan waktu yang dipilih
                            Text(text = dateTimeString)

                            // Tombol untuk memilih tanggal
                            Button(onClick = {
                                showDatePicker(
                                    context,
                                    year,
                                    month,
                                    day,
                                    onDateSet = { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                                        year = selectedYear
                                        month = selectedMonth
                                        day = selectedDayOfMonth

                                        showTimePicker(
                                            context,
                                            hour,
                                            minute,
                                            onTimeSet = { _, selectedHour, selectedMinute ->
                                                hour = selectedHour
                                                minute = selectedMinute

                                                // Gabungkan tanggal dan waktu ke ZonedDateTime (untuk timezone)
                                                val selectedDateTime = ZonedDateTime.of(
                                                    selectedYear,
                                                    selectedMonth + 1,
                                                    selectedDayOfMonth,
                                                    selectedHour,
                                                    selectedMinute,
                                                    0,
                                                    0,
                                                    ZoneOffset.UTC // Ensures the time is in UTC
                                                )

                                                // Format menjadi ISO 8601 dengan timezone
                                                val formatter = DateTimeFormatter.ISO_DATE_TIME
                                                val formattedDateTime =
                                                    selectedDateTime.format(formatter)

                                                // Update variabel availableUntil dan tampilkan
                                                availableUntil = formattedDateTime
                                                dateTimeString = formattedDateTime


                                            }
                                        )
                                    }
                                )
                            }) {
                                Text("Pilih Tanggal")
                            }

                            // Tampilkan waktu yang dipilih dalam format ISO
                            Text(text = "Waktu yang dipilih: $availableUntil")
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // button create
            if (!isImeVisible) {
                AnimatedVisibility(
                    visible = uiState.detail !is CreateOfferState.Detail.Loading
                ) {
                    Button(
                        onClick = {
                            if (selectType.isNotBlank())
                                viewModel.create(
                                    title = title,
                                    description = description,
                                    type = selectType,
                                    availableUntil = availableUntil,
                                    price = price,
                                    pickupArea = pickupArea,
                                    destinationArea = destinationArea,
                                    maxParticipants = maxParticipants
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


        }
    }
}


fun showDatePicker(
    context: Context,
    year: Int,
    month: Int,
    day: Int,
    onDateSet: (view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit
) {
    DatePickerDialog(context, onDateSet, year, month, day).show()
}

fun showTimePicker(
    context: Context,
    hour: Int,
    minute: Int,
    onTimeSet: (view: TimePicker?, hourOfDay: Int, minute: Int) -> Unit
) {
    TimePickerDialog(context, onTimeSet, hour, minute, true).show()
}

