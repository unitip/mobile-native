package com.unitip.mobile.features.social.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class SocialRoutes {
   @Serializable
   data class Index(val index: Int)
}