package com.unitip.mobile.features.setting.presentation.states

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val role: String = "",
    val detail: ProfileStateDetail = ProfileStateDetail.Initial,
)

sealed interface ProfileStateDetail {
    data object Initial : ProfileStateDetail
    data object Loading : ProfileStateDetail
    data object Success : ProfileStateDetail
    data class Failure(
        val message: String,
        val code: Int? = null
    ) : ProfileStateDetail
}