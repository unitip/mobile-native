package com.unitip.mobile.features.setting.presentation.states

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val role: String = "",

    val logoutDetail: LogoutDetail = LogoutDetail.Initial,
) {
    sealed interface LogoutDetail {
        data object Initial : LogoutDetail
        data object Loading : LogoutDetail
        data object Success : LogoutDetail
        data class Failure(
            val message: String,
            val code: Int? = null
        ) : LogoutDetail
    }
}

