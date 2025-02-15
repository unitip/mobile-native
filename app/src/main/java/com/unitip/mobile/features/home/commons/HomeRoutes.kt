package com.unitip.mobile.features.home.commons

import kotlinx.serialization.Serializable

sealed class HomeRoutes {
    @Serializable
    object Index

    @Serializable
    object Dashboard

    @Serializable
    object Jobs

    @Serializable
    object Offers

    @Serializable
    object Social

    @Serializable
    object Chats

    @Serializable
    object Profile
}