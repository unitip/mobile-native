package com.unitip.mobile.features.setting.presentation.states

import com.unitip.mobile.shared.domain.models.Session

data class EditProfileState(
    val session: Session = Session(),
    val editDetail: EditDetail = EditDetail.Initial
) {
    sealed interface EditDetail {
        data object Initial : EditDetail
        data object Loading : EditDetail
        data object Success : EditDetail
        data class Failure(val message: String) : EditDetail
    }
}
