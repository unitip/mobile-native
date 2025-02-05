package com.unitip.mobile.features.auth.data.dtos

import com.google.gson.annotations.SerializedName

@Deprecated("Gunakan hasil generated dari openapi")
data class LoginResponse(
    @SerializedName("need_role") val needRole: Boolean,
    val roles: List<String>,
    val id: String,
    val name: String,
    val email: String,
    val token: String,
    val role: String,
)