package com.unitip.mobile.features.account.presentation.states

import com.unitip.mobile.shared.domain.models.Session

data class UpdatePasswordState(
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