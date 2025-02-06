package com.unitip.mobile.features.offer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.unitip.mobile.features.offer.presentation.viewmodels.DetailApplicantOfferViewModel

@Composable
fun DetailApplicantScreen(
    offerId: String,
    applicantId: String,
    viewModel: DetailApplicantOfferViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val applicant by viewModel.applicant.collectAsState()

    when {
        uiState.isLoading -> {
            // Show loading state
        }
        uiState.error != null -> {
            // Show error state
        }
        else -> {
            // Show applicant details
            Column(modifier = Modifier.fillMaxSize()) {
                // Display applicant information
                Text(text = "Name: ${applicant.customer.name}")
                Text(text = "Pickup: ${applicant.pickupLocation}")
                Text(text = "Destination: ${applicant.destinationLocation}")
                Text(text = "Status: ${applicant.status}")
                Text(text = "Final Price: ${applicant.finalPrice}")
            }
        }
    }
}