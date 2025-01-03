package com.unitip.mobile.features.auth.core

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoutes {
    @Serializable
    object Index

    @Serializable
    data class PickRole(
        val roles: List<String>,
    )

    @Serializable
    object Unauthorized
}