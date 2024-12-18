package com.unitip.mobile.features.auth.data.models

data class LoginResult(
    val needRole: Boolean,
    val roles: List<String>,
)