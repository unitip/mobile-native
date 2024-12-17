package com.unitip.mobile.shared.utils

import com.unitip.mobile.shared.data.models.BadRequestError
import com.unitip.mobile.shared.data.models.ConflictError
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.models.InternalServerError
import com.unitip.mobile.shared.data.models.NotFoundError
import com.unitip.mobile.shared.data.models.UnauthorizedError
import kotlinx.serialization.json.Json
import retrofit2.Response

class ApiError {
    companion object {
        fun <T> mapToFailure(response: Response<T>): Failure {
            val errorBodyStr = response.errorBody()!!.string()

            return when (response.code()) {
                400 -> {
                    val badRequestError = Json.decodeFromString<BadRequestError>(errorBodyStr)
                    Failure(
                        message = badRequestError.errors.first().message,
                        code = 400
                    )
                }

                401 -> {
                    val unauthorizedError =
                        Json.decodeFromString<UnauthorizedError>(errorBodyStr)
                    Failure(
                        message = unauthorizedError.message,
                        code = 401
                    )
                }

                404 -> {
                    val notFoundError = Json.decodeFromString<NotFoundError>(errorBodyStr)
                    Failure(
                        message = notFoundError.message,
                        code = 404
                    )
                }

                409 -> {
                    val conflictError = Json.decodeFromString<ConflictError>(errorBodyStr)
                    Failure(
                        message = conflictError.message,
                        code = 409
                    )
                }

                500 -> {
                    val internalServerError =
                        Json.decodeFromString<InternalServerError>(errorBodyStr)
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
    }
}