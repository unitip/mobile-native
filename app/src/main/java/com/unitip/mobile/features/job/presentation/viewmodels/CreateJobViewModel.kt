package com.unitip.mobile.features.job.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.unitip.mobile.features.job.data.repositories.JobRepository
import com.unitip.mobile.features.job.presentation.states.CreateJobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateJobState())
    val uiState get() = _uiState

    fun create() {}
}