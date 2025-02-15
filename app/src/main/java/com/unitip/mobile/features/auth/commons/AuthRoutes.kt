package com.unitip.mobile.features.auth.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoutes {
    @Serializable
    object Index

    @Serializable
    data class PickRole(
        val email: String,
        val password: String,
        val roles: List<String>,
    )


    @Serializable
    object Unauthorized
}