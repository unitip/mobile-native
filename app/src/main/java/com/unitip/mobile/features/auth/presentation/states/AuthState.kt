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

//enum class AuthUiAction {
//    Initial,
//    Login,
//    Register,
//    SwitchAuthMode,
//}
//
//data class AuthUiState(
//    val action: AuthUiAction = AuthUiAction.Initial,
//    val status: UIStatus = UIStatus.Initial,
//
//    val message: String = "",
//    val isLogin: Boolean = true,
//    val needRole: Boolean = false,
//    val roles: List<String> = listOf(),
//)
