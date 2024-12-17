package com.unitip.mobile.features.auth.presentation.states

import com.unitip.mobile.core.ui.UIStatus

enum class AuthUiAction {
    Initial,
    Login,
    Register,
    SwitchAuthMode,
}

data class AuthUiState(
    val action: AuthUiAction = AuthUiAction.Initial,
    val status: UIStatus = UIStatus.Initial,

    val message: String = "",
    val isLogin: Boolean = true,
    val needRole: Boolean = false,
    val roles: List<String> = listOf(),
)
