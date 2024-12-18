package com.unitip.mobile.features.auth.data.dtos

data class RegisterPayload(
    val name: String,
    val email: String,
    val password: String,
)