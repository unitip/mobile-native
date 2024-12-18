package com.unitip.mobile.shared.presentation.compositional

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided!")
}