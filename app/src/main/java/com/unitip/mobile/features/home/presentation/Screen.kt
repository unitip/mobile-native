package com.unitip.mobile.features.home.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.BadgeHelp
import com.composables.icons.lucide.BriefcaseBusiness
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessagesSquare
import com.composables.icons.lucide.User
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
    val navigationItems = listOf(
        NavigationItem(
            "Dashboard",
            icon = Lucide.LayoutDashboard,
            route = Routes.Home.Dashboard
        ),
        NavigationItem(
            "Jobs",
            icon = Lucide.BriefcaseBusiness,
            route = Routes.Home.Jobs
        ),
        NavigationItem(
            "Offers",
            icon = Lucide.BadgeHelp,
            route = Routes.Home.Offers
        ),
        NavigationItem(
            "Chats",
            icon = Lucide.MessagesSquare,
            route = Routes.Home.Chats
        ),
        NavigationItem(
            "Profile",
            icon = Lucide.User,
            route = Routes.Home.Profile
        ),
    )

    val homeNavController = rememberNavController()

    CompositionLocalProvider(LocalHomeNavController provides homeNavController) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            bottomBar = {
                NavigationBar (
                    modifier = Modifier,
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ){
                    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    navigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            modifier = Modifier.padding(
                                start = (if (index == 0) 16 else 0).dp,
                                end = (if (index == navigationItems.size - 1) 16 else 0).dp
                            ),
                            selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                            onClick = {
                                homeNavController.navigate(item.route) {
                                    popUpTo(homeNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = null,modifier = Modifier.size(22.dp)) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.tertiaryContainer // Warna background luar saat dipilih

                            )
                        )
                    }
                }
            }
        ) {
            HomeNavigationGraph(
                modifier = Modifier.padding(it),
                navController = homeNavController,
                onNavigate = onNavigate,
            )
        }
    }
}