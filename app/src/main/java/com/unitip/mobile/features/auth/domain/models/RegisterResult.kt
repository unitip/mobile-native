package com.unitip.mobile.features.auth.domain.models

data class RegisterResult (
    val id: String,
    val name: String,
    val email: String,
    val token: String,
    val role: String
)