package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.unitip.mobile.features.job.commons.JobRoutes
import com.unitip.mobile.features.job.presentation.states.ApplyJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Deprecated("Sudah tidak digunakan")
@HiltViewModel
class ApplyJobViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
//    private val singleJobRepository: SingleJobRepository
) : ViewModel() {
    private val parameters = savedStateHandle.toRoute<JobRoutes.Apply>()

    private val _uiState = MutableStateFlow(ApplyJobState())
    val uiState get() = _uiState.asStateFlow()

    fun apply(price: Int) = viewModelScope.launch {
        _uiState.update { it.copy(detail = ApplyJobState.Detail.Loading) }

//        singleJobRepository.apply(
//            jobId = parameters.jobId,
//            price = price
//        ).fold(
//            ifLeft = { left ->
//                _uiState.update {
//                    it.copy(
//                        detail = ApplyJobState.Detail.Failure(message = left.message)
//                    )
//                }
//            },
//            ifRight = {
//                _uiState.update {
//                    it.copy(detail = ApplyJobState.Detail.Success)
//                }
//            }
//        )
    }
}