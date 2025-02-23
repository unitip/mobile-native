package com.unitip.mobile.features.activity.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.activity.data.repositories.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unitip.mobile.features.activity.presentation.state.CreateActivityState as State

@HiltViewModel
class CreateActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    fun upload(
        content: String
    ) = viewModelScope.launch {
        _uiState.update { it.copy(detail = State.Detail.Loading) }
        activityRepository
            .createActivity(content = content)
            .onLeft { left ->
                _uiState.update {
                    it.copy(
                        detail = State.Detail.Failure(
                            message = left.message
                        )
                    )
                }
            }
            .onRight {
                _uiState.update {
                    it.copy(detail = State.Detail.Success)
                }
            }
    }
}