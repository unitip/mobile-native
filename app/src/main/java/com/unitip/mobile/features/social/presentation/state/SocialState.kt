package com.unitip.mobile.features.social.presentation.state

import com.unitip.mobile.features.social.domain.models.SocialActivity

data class SocialState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(
            val activities: List<SocialActivity>
        ) : Detail
        data class Failure(
            val message: String
        ) : Detail
    }
}
