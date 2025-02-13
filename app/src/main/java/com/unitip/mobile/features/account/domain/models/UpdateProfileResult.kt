package com.unitip.mobile.features.account.domain.models

import com.unitip.mobile.shared.commons.constants.GenderConstant

data class UpdateProfileResult(
    val id: String,
    val name: String,
    val gender: GenderConstant
)
