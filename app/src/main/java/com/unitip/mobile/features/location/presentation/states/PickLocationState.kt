package com.unitip.mobile.features.location.presentation.states

import android.location.Location

data class PickLocationState(
    val getLastLocationDetail: GetLastLocationDetail = GetLastLocationDetail.Initial,
) {
    sealed interface GetLastLocationDetail {
        data object Initial : GetLastLocationDetail
        data class SuccessGetLastLocation(
            val lastLocation: Location
        ) : GetLastLocationDetail
    }
}
