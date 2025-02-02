package com.unitip.mobile.features.setting.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingRoutes {

    @Serializable
    object Edit

    @Serializable
    object OrderHistory
}