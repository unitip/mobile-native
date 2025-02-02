package com.unitip.mobile.features.setting.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingRoutes {

    @Serializable
    object EditProfile

    @Serializable
    object EditPassword

    @Serializable
    object OrderHistory
}