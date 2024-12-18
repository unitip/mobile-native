package com.unitip.mobile.features.setting.presentation.states

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val detail: ProfileDetail = ProfileDetail.Initial,
)

sealed interface ProfileDetail {
    data object Initial : ProfileDetail
}