package com.unitip.mobile.features.auth.data.dtos

@Deprecated("Gunakan hasil generated dari openapi")
data class LoginPayload(
    val email: String,
    val password: String,
    val role: String? = null,
)