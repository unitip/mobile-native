package com.unitip.mobile.features.auth.data.dtos

data class LoginPayload(
    val email: String,
    val password: String,
    val role: String? = null,
)