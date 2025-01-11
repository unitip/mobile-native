package com.unitip.mobile.features.auth.data.repositories

import arrow.core.Either
import arrow.core.left
import com.unitip.mobile.features.auth.data.dtos.LoginPayload
import com.unitip.mobile.features.auth.data.dtos.RegisterPayload
import com.unitip.mobile.features.auth.data.sources.AuthApi
import com.unitip.mobile.features.auth.domain.models.LoginResult
import com.unitip.mobile.features.auth.domain.models.RegisterResult
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.data.models.Failure
import com.unitip.mobile.shared.data.models.Session
import com.unitip.mobile.shared.utils.extensions.mapToFailure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
) {
    suspend fun login(
        email: String,
        password: String,
        role: String? = null
    ): Either<Failure, LoginResult> {
        try {
            val response =
                authApi.login(
                    LoginPayload(
                        email = email,
                        password = password,
                        role = role
                    )
                )
            val result = response.body()

            if (response.isSuccessful && result != null) {
                if (!result.needRole)
                    sessionManager.create(
                        Session(
                            name = result.name,
                            email = result.email,
                            token = result.token,
                            role = result.role,
                        )
                    )

                return Either.Right(
                    LoginResult(
                        needRole = result.needRole,
                        roles = result.roles,
                    )
                )
            }

            return Either.Left(response.mapToFailure())
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Either<Failure, RegisterResult> {
        try {
            //Melakukan pengiriman regiter ke server
            val response=
                authApi.register(
                    RegisterPayload(
                        name= name,
                        email = email,
                        password = password
                    )
                )
            val result = response.body()

            if (response.isSuccessful && result!=null){
                // setelah register, langsung masukkin ke session manager
                sessionManager.create(
                    Session(
                        name = result.name,
                        email = result.email,
                        token = result.token,
                        role = result.role,
                    )
                )

                return Either.Right(
                    RegisterResult(
                        id = result.id,
                        name = result.name,
                        email = result.email,
                        token = result.token,
                        role = result.role
                    )
                )
            }
            // Jika gagal melakukan registrasi
            return Either.Left(Failure(message = "Gagal melakukan Registrasi"))
        }catch (e: Exception){
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga"))
        }
    }
}