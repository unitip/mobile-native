package com.unitip.mobile.features.auth.presentation.states

data class AuthState(
    val isLogin: Boolean = true,
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class SuccessWithPickRole(val roles: List<String>) : Detail
        data class Failure(val message: String) : Detail
    }
}

