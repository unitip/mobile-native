package com.unitip.mobile.features.home.presentation.sceens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.BadgeHelp
import com.composables.icons.lucide.BriefcaseBusiness
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessagesSquare
import com.composables.icons.lucide.User
import com.unitip.mobile.features.home.presentation.components.CustomNavbar
import com.unitip.mobile.shared.presentation.compositional.LocalHomeNavController
import com.unitip.mobile.shared.presentation.navigation.HomeNavigationGraph
import com.unitip.mobile.shared.presentation.navigation.Routes

private data class NavigationItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    val route: T
)

@Composable
fun HomeScreen(
    onNavigate: (route: Any) -> Unit = {},
) {

    val homeNavController = rememberNavController()

    CompositionLocalProvider(LocalHomeNavController provides homeNavController) {
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
                sides = WindowInsetsSides.Bottom
            )
        ) {
            Column(modifier = Modifier.padding(it)) {
                HomeNavigationGraph(
                    modifier = Modifier.weight(1f),
                    navController = homeNavController,
                    onNavigate = onNavigate,
                )
                CustomNavbar()
            }
        }
    }
}

