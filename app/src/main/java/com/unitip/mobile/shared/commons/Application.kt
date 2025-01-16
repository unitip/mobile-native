package com.unitip.mobile.shared.commons

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.shared.commons.compositional.LocalNavController
import com.unitip.mobile.shared.presentation.navigation.ApplicationNavigationGraph
import com.unitip.mobile.shared.presentation.ui.theme.UnitipTheme

@Composable
fun Application(isAuthenticated: Boolean) {
    UnitipTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        Surface {
            val navController = rememberNavController()

            CompositionLocalProvider(LocalNavController provides navController) {
                ApplicationNavigationGraph(
                    navController = navController,
                    isAuthenticated = isAuthenticated,
                )
            }
        }
    }
}