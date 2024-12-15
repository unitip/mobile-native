package com.unitip.mobile.core.navigation

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

    // chat
    @Serializable
    object Chat

    // job
    @Serializable
    object CreateJob

    // offer
    @Serializable
    object CreateOffer

    // setting
}

