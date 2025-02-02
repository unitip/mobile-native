package com.unitip.mobile.features.account.presentation.states

import com.unitip.mobile.shared.domain.models.Session

data class ProfileState(
    val session: Session = Session(),

    val logoutDetail: LogoutDetail = LogoutDetail.Initial,
) {
    sealed interface LogoutDetail {
        data object Initial : LogoutDetail
        data object Loading : LogoutDetail
        data object Success : LogoutDetail
        data class Failure(
            val message: String,
            val code: Int? = null
        ) : LogoutDetail
    }
}

