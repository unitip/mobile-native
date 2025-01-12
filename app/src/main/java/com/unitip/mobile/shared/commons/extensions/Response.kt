package com.unitip.mobile.shared.commons.extensions

import com.google.gson.Gson
import com.unitip.mobile.shared.data.dto.BadRequestError
import com.unitip.mobile.shared.data.dto.ConflictError
import com.unitip.mobile.shared.data.dto.InternalServerError
import com.unitip.mobile.shared.data.dto.NotFoundError
import com.unitip.mobile.shared.data.dto.UnauthorizedError
import com.unitip.mobile.shared.domain.models.Failure
import retrofit2.Response

fun <T> Response<T>.mapToFailure(): Failure {
    val errorBodyStr = this.errorBody()!!.string()
    val gson = Gson()

    return when (this.code()) {
        400 -> {
            val badRequestError = gson.fromJson(errorBodyStr, BadRequestError::class.java)
            Failure(
                message = badRequestError.errors.first().message,
                code = 400
            )
        }

        401 -> {
            val unauthorizedError = gson.fromJson(errorBodyStr, UnauthorizedError::class.java)
            Failure(
                message = unauthorizedError.message,
                code = 401
            )
        }

        404 -> {
            val notFoundError = gson.fromJson(errorBodyStr, NotFoundError::class.java)
            Failure(
                message = notFoundError.message,
                code = 404
            )
        }

        409 -> {
            val conflictError = gson.fromJson(errorBodyStr, ConflictError::class.java)
            Failure(
                message = conflictError.message,
                code = 409
            )
        }

        500 -> {
            val internalServerError = gson.fromJson(errorBodyStr, InternalServerError::class.java)
            Failure(
                message = internalServerError.message,
                code = 500
            )
        }

        else -> Failure(
            message = "Terjadi kesalahan tak terduga!",
            code = 500
        )
    }
}