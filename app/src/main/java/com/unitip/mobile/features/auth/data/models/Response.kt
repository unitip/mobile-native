package com.unitip.mobile.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("need_role") val needRole: Boolean,
    val roles: List<String>,
    val id: String,
    val name: String,
    val email: String,
    val token: String,
)

data class AuthResponse(
    val name: String,
    val email: String,
    val token: String,
)