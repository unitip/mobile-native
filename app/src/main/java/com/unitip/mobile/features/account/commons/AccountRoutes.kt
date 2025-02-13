package com.unitip.mobile.features.account.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class AccountRoutes {
    @Serializable
    object UpdateProfile

    @Serializable
    object UpdatePassword

    @Serializable
    object ChangeRole

    @Serializable
    object OrderHistory

}