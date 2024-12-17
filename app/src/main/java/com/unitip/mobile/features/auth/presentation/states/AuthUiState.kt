package com.unitip.mobile.features.auth.presentation.states

import com.unitip.mobile.core.ui.UIStatus

enum class AuthUiAction {
    Initial,
    Login,
    Register,
}

data class AuthUiState(
    val status: UIStatus = UIStatus.Initial,
    val action: AuthUiAction = AuthUiAction.Initial,

    val isLogin: Boolean = true,
)
