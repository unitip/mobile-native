package com.unitip.mobile.features.account.presentation.states

data class ChangeRoleState(
    val getRoleDetail: GetRoleDetail = GetRoleDetail.Initial,
    val changeRoleDetail: ChangeRoleDetail = ChangeRoleDetail.Initial
) {
    sealed interface GetRoleDetail {
        data object Initial : GetRoleDetail
        data object Loading : GetRoleDetail
        data class Success(val roles: List<String>) : GetRoleDetail
        data class Failure(val message: String) : GetRoleDetail
    }

    sealed interface ChangeRoleDetail {
        data object Initial : ChangeRoleDetail
        data object Loading : ChangeRoleDetail
        data object Success : ChangeRoleDetail
        data class Failure(val message: String) : ChangeRoleDetail
    }
}