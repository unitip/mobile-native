package com.unitip.mobile.shared.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val name: String,
    val email: String,
    val token: String,
)
