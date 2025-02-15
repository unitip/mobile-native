package com.unitip.mobile.shared.commons.compositional

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalHomeNavController = compositionLocalOf<NavController> {
    error("No HomeNavController provided!")
}