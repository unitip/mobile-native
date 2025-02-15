package com.unitip.mobile.shared.domain.models

import com.unitip.mobile.shared.commons.constants.GenderConstant

data class Session(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val role: String = "",
    val gender: GenderConstant = GenderConstant.NotSpecified
)
