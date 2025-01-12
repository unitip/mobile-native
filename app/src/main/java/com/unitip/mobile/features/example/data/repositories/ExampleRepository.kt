package com.unitip.mobile.features.example.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.example.data.sources.ExampleApi
import com.unitip.mobile.features.example.domain.models.User
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleRepository @Inject constructor(
    private val exampleApi: ExampleApi
) {
    suspend fun getAllUsers(): Either<Failure, List<User>> = try {
        val response = exampleApi.getAllUsers()
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(result.users.map { user ->
                User(
                    id = user.id,
                    name = user.name,
                    age = user.age
                )
            })

            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = e.message ?: "Terjadi kesalahan tak terduga!"))
    }
}