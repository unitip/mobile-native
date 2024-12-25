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
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
                sides = WindowInsetsSides.Bottom
            ),
//            bottomBar = {
//                Column {
//                    HorizontalDivider(
//                        color = MaterialTheme.colorScheme.secondaryContainer,
//                        thickness = 1.dp,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    NavigationBar(
//                        modifier = Modifier
//                            .height(100.dp)
//                            .padding(top = 1.dp),
//                        containerColor = Color.Transparent
//                    ) {
//                        val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
//                        val currentDestination = navBackStackEntry?.destination
//
//                        navigationItems.forEachIndexed { index, item ->
//                            NavigationBarItem(
//                                modifier = Modifier.padding(
//                                    start = (if (index == 0) 16 else 0).dp,
//                                    end = (if (index == navigationItems.size - 1) 16 else 0).dp
//                                ),
//                                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
//                                onClick = {
//                                    homeNavController.navigate(item.route) {
//                                        popUpTo(homeNavController.graph.findStartDestination().id) {
//                                            saveState = true
//                                        }
//                                        launchSingleTop = true
//                                        restoreState = true
//                                    }
//                                },
//                                icon = {
//                                    Icon(
//                                        item.icon,
//                                        contentDescription = item.title,
//                                        modifier = if (currentDestination?.hierarchy?.any {
//                                                it.hasRoute(
//                                                    item.route::class
//                                                )
//                                            } == true) {
//                                            Modifier.size(26.dp)
//                                        } else {
//                                            Modifier.size(22.dp)
//                                        },
//                                    )
//                                },
//                                colors = NavigationBarItemDefaults.colors(
//                                    selectedIconColor = MaterialTheme.colorScheme.primary,
//                                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(
//                                        alpha = 0.5f
//                                    ),
//                                    indicatorColor = Color.Transparent
//                                )
//                            )
//                        }
//                    }
//                }
//            }
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

