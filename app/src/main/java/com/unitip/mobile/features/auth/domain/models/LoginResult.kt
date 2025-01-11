package com.unitip.mobile.features.auth.domain.models

data class LoginResult(
    val needRole: Boolean,
    val roles: List<String>,
)