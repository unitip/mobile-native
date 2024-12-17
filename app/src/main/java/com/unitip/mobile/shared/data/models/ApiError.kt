package com.unitip.mobile.shared.data.models

data class BadRequestItem(
    val path: String,
    val message: String,
)

data class BadRequestError(
    val errors: List<BadRequestItem>,
)

data class UnauthorizedError(
    val message: String,
)

data class NotFoundError(
    val message: String,
)

data class ConflictError(
    val message: String,
)

data class InternalServerError(
    val message: String,
)
