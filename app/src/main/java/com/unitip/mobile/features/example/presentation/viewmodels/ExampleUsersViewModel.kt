package com.unitip.mobile.features.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.example.data.repositories.ExampleRepository
import com.unitip.mobile.features.example.presentation.states.ExampleUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleUsersViewModel @Inject constructor(
    private val exampleRepository: ExampleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExampleUsersState())
    val uiState get() = _uiState.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(detail = ExampleUsersState.Detail.Loading) }
            exampleRepository.getAllUsers().fold(
                ifLeft = { left ->
                    _uiState.update {
                        it.copy(
                            detail = ExampleUsersState.Detail.Failure(
                                message = left.message,
                                code = left.code
                            )
                        )
                    }
                },
                ifRight = { right ->
                    _uiState.update {
                        it.copy(
                            detail = ExampleUsersState.Detail.Success(users = right)
                        )
                    }
                }
            )
        }
    }
}