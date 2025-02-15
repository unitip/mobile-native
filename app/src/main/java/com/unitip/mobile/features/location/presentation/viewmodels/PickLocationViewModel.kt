package com.unitip.mobile.features.location.presentation.viewmodels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.unitip.mobile.features.location.presentation.states.PickLocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PickLocationViewModel @Inject constructor(
    private val application: Application,
    private val fusedLocation: FusedLocationProviderClient
) : ViewModel() {
    companion object {
        private const val TAG = "PickLocationViewModel"
    }

    private val _uiState = MutableStateFlow(PickLocationState())
    val uiState get() = _uiState.asStateFlow()

    fun resetLastLocationState() = _uiState.update {
        it.copy(getLastLocationDetail = PickLocationState.GetLastLocationDetail.Initial)
    }

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                application, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocation.lastLocation.addOnSuccessListener { location ->
            _uiState.update {
                it.copy(
                    getLastLocationDetail = PickLocationState.GetLastLocationDetail.SuccessGetLastLocation(
                        lastLocation = location
                    )
                )
            }
        }
    }
}