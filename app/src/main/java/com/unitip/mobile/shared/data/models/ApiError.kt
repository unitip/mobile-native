package com.unitip.mobile.shared.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BadRequestItem(
    val path: String,
    val message: String,
)

@Serializable
data class BadRequestError(
    val errors: List<BadRequestItem>,
)

@Serializable
data class UnauthorizedError(
    val message: String,
)

@Serializable
data class NotFoundError(
    val message: String,
)

@Serializable
data class ConflictError(
    val message: String,
)

@Serializable
data class InternalServerError(
    val message: String,
)
