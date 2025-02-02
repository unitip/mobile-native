package com.unitip.mobile.features.offer.commons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.unitip.mobile.features.offer.domain.models.Offer
import com.unitip.mobile.features.offer.presentation.screens.ApplyOfferScreen
import com.unitip.mobile.features.offer.presentation.screens.CreateOfferScreen
import com.unitip.mobile.features.offer.presentation.screens.DetailOfferScreen

fun NavGraphBuilder.offerNavigation(navController: NavController) {
    composable<OfferRoutes.Create> { CreateOfferScreen() }

    composable<OfferRoutes.Detail> {
        val data = it.toRoute<OfferRoutes.Detail>()
        DetailOfferScreen(offerId = data.offerId)
    }

    composable<OfferRoutes.ApplyOffer> {
        val data = it.toRoute<OfferRoutes.ApplyOffer>()
        ApplyOfferScreen(
            offerId = data.offerId,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}