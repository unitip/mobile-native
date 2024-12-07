package com.unitip.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.unitip.mobile.navigation.RootNavigationGraph
import com.unitip.mobile.ui.theme.UnitipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitipTheme {
                Surface {
                    val navController = rememberNavController()
                    RootNavigationGraph(navController = navController)
                }
            }
        }
    }
}
