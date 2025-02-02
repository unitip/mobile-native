package com.unitip.mobile.features.account.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class AccountRoutes {
    @Serializable
    object EditProfile

    @Serializable
    object EditPassword

    @Serializable
    object OrderHistory
}