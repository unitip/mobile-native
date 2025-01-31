package com.unitip.mobile.features.offer.commons

import com.google.gson.annotations.SerializedName

data class PageInfo(
    val count: Int,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)