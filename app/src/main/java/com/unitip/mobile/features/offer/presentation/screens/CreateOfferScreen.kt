package com.unitip.mobile.features.offer.presentation.screens

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.FastForward
import com.composables.icons.lucide.FileWarning
import com.composables.icons.lucide.Lucide
import com.google.gson.annotations.SerializedName
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import com.unitip.mobile.features.offer.commons.OfferConstans
import com.unitip.mobile.features.offer.commons.OfferRoutes
import com.unitip.mobile.features.offer.presentation.states.CreateOfferState
import com.unitip.mobile.features.offer.presentation.viewmodels.CreateOfferViewModel
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.components.CustomCard
import com.unitip.mobile.shared.presentation.components.CustomIconButton
import com.unitip.mobile.shared.presentation.components.CustomTextButton
import com.unitip.mobile.shared.presentation.components.DropdownTypeOrders
import com.unitip.mobile.shared.presentation.components.OrderType

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateOfferScreen(
    viewModel: CreateOfferViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val isImeVisible = WindowInsets.isImeVisible
    val listState = rememberLazyListState()
    val firstVisibleListItemIndex = remember{ derivedStateOf { listState.firstVisibleItemIndex }}

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf(0) }
    var type by rememberSaveable { mutableStateOf("") }
    var pickupArea by rememberSaveable { mutableStateOf("") }
    var destinationArea by rememberSaveable { mutableStateOf("") }
    var availableUntil by rememberSaveable { mutableStateOf("") }
    var maxParticipants by rememberSaveable { mutableStateOf(0) }
    var isSelectType by rememberSaveable { mutableStateOf(false) }
    var selectedService by rememberSaveable { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail){
            when (this) {
                is CreateOfferState.Detail.Failure -> {
                    snackbarHostState.showSnackbar(
                        message= message,
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
            ){
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
            LazyColumn  (
                modifier = Modifier
                    .weight(1f)
                    .imePadding(),
                state = listState
            ){
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
                item{
                    Text(
                        text = "Masukkan beberapa informasi berikut untuk membuat penawaran baru",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }

                item {
                    CustomCard(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ){
                        Column {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = uiState.detail !is CreateOfferState.Detail.Loading
                                    ){
                                        isSelectType = !isSelectType
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ){
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

                                Column (
                                    modifier = Modifier.weight(1f)
                                ){
                                    Text(
                                        text = "Jenis Layanan",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = when(selectedService.isBlank()){
                                            true -> "Pilih jenis layanan"
                                            else -> OfferConstans.services.find {
                                                service -> service.value == selectedService
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
                            // Sampe sini ya ren agak ngantuk ni saya
                            AnimatedVisibility(
                                visible = isSelectType
                            ) {

                            }

                        }
                    }
                }


            }
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                placeholder = {
                    Text(text = "Judul penawaran")
                })
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                val orderTypes = listOf(
                    OrderType("Jasa Titip", Lucide.Check),
                    OrderType("Antar Jemput", Lucide.FileWarning),
                    OrderType("Lain - lain", Lucide.FastForward)
                )
                // Panggil DropdownTypeOrders di sini
                DropdownTypeOrders(orderTypes = orderTypes)
            }
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Biaya penanganan")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Lokasi Penawaran")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Area Pengantaran")
                })
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                placeholder = {
                    Text(text = "Catatan tambahan")
                },
                minLines = 5,
            )

            // available until
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Rounded.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .size(40.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Tersedia hingga") },
                supportingContent = { Text(text = "16/12/2024, 10.10") },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { }
            )

            // type
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Rounded.Done,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .size(40.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = "Jenis penawaran") },
                supportingContent = { Text(text = "Delivery") },
                modifier = Modifier
                    .clickable { }
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
            ) {
                OutlinedButton(onClick = { navController.popBackStack() }) {
                    Text(text = "Batal")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {}) {
                    Text(text = "Create")
                }
            }
        }
    }
}

