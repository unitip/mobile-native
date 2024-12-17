package com.unitip.mobile.features.auth.data.models

data class LoginPayload(
    val email: String,
    val password: String,
    val role: String? = null,
)

data class RegisterPayload(
    val name: String,
    val email: String,
    val password: String,
)