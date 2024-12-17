package com.unitip.mobile.features.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState get() = _uiState

    fun switchAuthMode() {
        _uiState.value = with(uiState.value) {
            copy(isLogin = !isLogin)
        }
    }

    fun fetchData() {
        _uiState.value = with(uiState.value) {
            copy(details = TestUiDetails.Loading)
        }

        viewModelScope.launch {
            delay(2000)

            _uiState.value = with(uiState.value) {
                copy(
                    sharedList = sharedList.plus(listOf("data1", "data2", "data3")),
                    details = TestUiDetails.SuccessFetching
                )
            }
        }
    }

    fun login() {
        _uiState.value = with(uiState.value) {
            copy(details = TestUiDetails.Loading)
        }

        viewModelScope.launch {
            delay(2000)

            if (Random().nextBoolean()) {
                _uiState.value = with(uiState.value) {
                    copy(details = TestUiDetails.Success(data = "success login"))
                }
            } else {
                _uiState.value = with(uiState.value) {
                    copy(details = TestUiDetails.Failure(message = "failed login"))
                }
            }
        }
    }
}