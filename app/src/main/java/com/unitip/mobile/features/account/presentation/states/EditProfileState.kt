package com.unitip.mobile.features.account.presentation.states

import com.unitip.mobile.shared.domain.models.Session

data class EditProfileState(
    val session: Session = Session(),
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(val message: String) : Detail
    }
}
