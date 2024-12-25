package com.unitip.mobile.shared.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object Auth

    @Serializable
    object Home {
        @Serializable
        object Dashboard

        @Serializable
        object Jobs

        @Serializable
        object Offers

        @Serializable
        object Chats

        @Serializable
        object Profile
    }

    // auth
    @Serializable
    data class PickRole(
        val roles: List<String>,
    )

    @Serializable
    object Unauthorized

    // chat
    @Serializable
    object Chat

    // offer
    @Serializable
    object CreateOffer

    // setting

    // test
    @Serializable
    object Test
}

