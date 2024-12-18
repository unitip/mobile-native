package com.unitip.mobile.features.auth.presentation.states

data class AuthState(
    val isLogin: Boolean = true,
    val detail: AuthStateDetail = AuthStateDetail.Initial
)

sealed interface AuthStateDetail {
    data object Initial : AuthStateDetail
    data object Loading : AuthStateDetail
    data object Success : AuthStateDetail
    data class SuccessWithPickRole(val roles: List<String>) : AuthStateDetail
    data class Failure(val message: String) : AuthStateDetail
}