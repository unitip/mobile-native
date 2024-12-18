package com.unitip.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.core.navigation.ApplicationNavigationGraph
import com.unitip.mobile.core.ui.theme.UnitipTheme
import com.unitip.mobile.shared.data.repositories.SessionRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isAuthenticated = sessionRepository.read().fold(
            ifLeft = { false },
            ifRight = { true }
        )

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
