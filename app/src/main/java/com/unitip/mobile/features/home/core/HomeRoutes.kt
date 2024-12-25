package com.unitip.mobile.features.home.core

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
    object Chats

    @Serializable
    object Profile
}