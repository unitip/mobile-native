package com.unitip.mobile.features.auth.data.dtos

data class RegisterResponse(
    val id: String,
    val name: String,
    val email: String,
    val token: String,
)
