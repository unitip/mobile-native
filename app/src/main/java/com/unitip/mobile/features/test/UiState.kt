package com.unitip.mobile.features.test

data class TestUiState(
    val isLogin: Boolean = true,
    val sharedList: List<String> = emptyList(),
    val details: TestUiDetails = TestUiDetails.Initial,
)

sealed interface TestUiDetails {
    data object Initial : TestUiDetails
    data object Loading : TestUiDetails
    data class Success(val data: String) : TestUiDetails
    data class Failure(val message: String) : TestUiDetails
    data object SuccessFetching : TestUiDetails
}