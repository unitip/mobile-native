package com.unitip.mobile.shared.domain.models

data class Failure(
    val message: String,
    val code: Int = 0,
)
