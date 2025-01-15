package com.unitip.mobile.shared.domain.models

data class Session(
    val name: String,
    val email: String,
    val token: String,
    val role: String,
)
