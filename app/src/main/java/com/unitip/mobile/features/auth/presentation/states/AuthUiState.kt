package com.unitip.mobile.features.auth.presentation.states

import com.unitip.mobile.core.ui.UIStatus

data class AuthUiState(
    val status: UIStatus = UIStatus.Loading,
    val isLogin: Boolean = true,
)
