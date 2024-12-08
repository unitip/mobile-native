package com.unitip.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.core.navigation.ApplicationNavigationGraph
import com.unitip.mobile.core.ui.theme.UnitipTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitipTheme(
                darkTheme = false,
            ) {
                Surface {
                    val navController = rememberNavController()
                    ApplicationNavigationGraph(navController = navController)
                }
            }
        }
    }
}
