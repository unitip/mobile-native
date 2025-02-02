package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateJobState())
    val uiState get() = _uiState

    fun resetState() {
        _uiState.value = with(uiState.value) {
            copy(detail = CreateJobState.Detail.Initial)
        }
    }

    fun create(
        title: String,
        destinationLocation: String,
        destinationLatitude: Double?,
        destinationLongitude: Double?,
        note: String,
        service: String,
        pickupLocation: String,
        pickupLatitude: Double?,
        pickupLongitude: Double?
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = CreateJobState.Detail.Loading) }
        jobRepository.create(
            title = title,
            destinationLocation = destinationLocation,
            destinationLatitude = destinationLatitude,
            destinationLongitude = destinationLongitude,
            note = note,
            service = service,
            pickupLocation = pickupLocation,
            pickupLatitude = pickupLatitude,
            pickupLongitude = pickupLongitude
        ).fold(
            ifLeft = { left ->
                _uiState.update {
                    it.copy(
                        detail = CreateJobState.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            },
            ifRight = {
                _uiState.update {
                    it.copy(detail = CreateJobState.Detail.Success)
                }
            }
        )
    }

//    fun createSingleJob(
//        title: String,
//        note: String,
//        pickupLocation: String,
//        destination: String,
//        service: String
//    ) = viewModelScope.launch {
//        _uiState.value = with(uiState.value) {
//            copy(detail = CreateJobState.Detail.Loading)
//        }
//
//        when (service) {
//            "antar-jemput" -> {
//                singleJobRepository.create(
//                    title = title,
//                    note = note,
//                    pickupLocation = pickupLocation,
//                    destination = destination,
//                    service = service
//                ).fold(
//                    ifLeft = {
//                        _uiState.value = with(uiState.value) {
//                            copy(detail = CreateJobState.Detail.Failure(message = it.message))
//                        }
//                    },
//                    ifRight = {
//                        _uiState.value = with(uiState.value) {
//                            copy(detail = CreateJobState.Detail.Success)
//                        }
//                    }
//                )
//            }
//
//            else -> {
//                _uiState.update {
//                    it.copy(
//                        detail = CreateJobState.Detail.Failure(message = "Not implemented yet!")
//                    )
//                }
//            }
//        }
//    }
//
//    fun createMultiJob(
//        title: String,
//        note: String,
//        pickupLocation: String,
//        service: String
//    ) = viewModelScope.launch {
//        _uiState.value = with(uiState.value) {
//            copy(detail = CreateJobState.Detail.Loading)
//        }
//        multiJobRepository.create(
//            title = title,
//            note = note,
//            pickupLocation = pickupLocation,
//            service = service
//        ).fold(
//            ifLeft = {
//                _uiState.value = with(uiState.value) {
//                    copy(detail = CreateJobState.Detail.Failure(message = it.message))
//                }
//            },
//            ifRight = {
//                _uiState.value = with(uiState.value) {
//                    copy(detail = CreateJobState.Detail.Success)
//                }
//            })
//    }
}