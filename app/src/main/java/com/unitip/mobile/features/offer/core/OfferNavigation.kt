package com.unitip.mobile.features.offer.core

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.offer.presentation.screens.CreateOfferScreen

fun NavGraphBuilder.offerNavigation() {
    composable<OfferRoutes.Create> { CreateOfferScreen() }
}