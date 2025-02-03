package com.unitip.mobile.features.account.presentation.states

data class ChangeRoleState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(val roles: List<String>) : Detail
        data class Failure(val message: String) : Detail
    }
}