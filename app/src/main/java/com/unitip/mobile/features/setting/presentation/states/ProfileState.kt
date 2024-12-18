package com.unitip.mobile.features.setting.presentation.states

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val detail: ProfileDetail = ProfileDetail.Initial,
)

sealed interface ProfileDetail {
    data object Initial : ProfileDetail
    data object Loading : ProfileDetail
    data object Success : ProfileDetail
    data class Failure(val message: String) : ProfileDetail
}