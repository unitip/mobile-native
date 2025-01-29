package com.unitip.mobile

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.unitip.mobile.shared.commons.Application
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.data.repositories.MqttRepository
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mqttRepository: MqttRepository

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mqttRepository.initialize()

        /**
         * konfigurasi osmdroid
         */
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )
        Configuration.getInstance().apply {
            userAgentValue = "unitip"
        }

        /**
         * request location permission
         */
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        /**
         * memulai aplikasi compose
         */
        setContent {
            Application(
                isAuthenticated = sessionManager.isAuthenticated()
            )
        }
    }
}
