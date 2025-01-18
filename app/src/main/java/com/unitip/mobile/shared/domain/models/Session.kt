package com.unitip.mobile.shared.domain.models

data class Session(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val role: String = "",
)
