package com.unitip.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.presentation.navigation.ApplicationNavigationGraph
import com.unitip.mobile.shared.presentation.ui.theme.UnitipTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isAuthenticated = sessionManager.read() != null

        setContent {
            UnitipTheme(
                darkTheme = false,
                // dynamicColor = false,
            ) {
                Surface {
                    val navController = rememberNavController()
                    ApplicationNavigationGraph(
                        navController = navController,
                        isAuthenticated = isAuthenticated,
                    )
                }
            }
        }
    }
}
