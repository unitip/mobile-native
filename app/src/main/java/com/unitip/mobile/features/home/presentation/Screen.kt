package com.unitip.mobile.features.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.core.navigation.HomeNavigationGraph
import com.unitip.mobile.core.navigation.Routes

private data class NavigationItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    val route: T
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onNavigate: (route: Any) -> Unit = {},
) {
    val navigationItems = listOf(
        NavigationItem(
            "Dashboard", Icons.TwoTone.Home,
            route = Routes.Home.Dashboard
        ),
        NavigationItem(
            "Jobs", Icons.TwoTone.LocationOn,
            route = Routes.Home.Jobs
        ),
        NavigationItem(
            "Offers", Icons.TwoTone.Check,
            route = Routes.Home.Offers
        ),
        NavigationItem(
            "Chats", Icons.TwoTone.Email,
            route = Routes.Home.Chats
        ),
        NavigationItem(
            "Profile", Icons.TwoTone.Person,
            route = Routes.Home.Profile
        ),
    )

    val homeNavController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            NavigationBar {
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
                        icon = { Icon(item.icon, contentDescription = null) }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            HomeNavigationGraph(
                navController = homeNavController,
                onNavigate = onNavigate,
            )
        }
    }
}