package com.unitip.mobile.features.account.presentation.states

data class ChangeRoleState(
    val getDetail: GetDetail = GetDetail.Initial,
    val changeDetail: ChangeDetail = ChangeDetail.Initial
) {
    sealed interface GetDetail {
        data object Initial : GetDetail
        data object Loading : GetDetail
        data class Success(
            val roles: List<String>
        ) : GetDetail

        data class Failure(
            val message: String
        ) : GetDetail
    }

    sealed interface ChangeDetail {
        data object Initial : ChangeDetail
        data object Loading : ChangeDetail
        data object Success : ChangeDetail
        data class Failure(
            val message: String
        ) : ChangeDetail
    }
}