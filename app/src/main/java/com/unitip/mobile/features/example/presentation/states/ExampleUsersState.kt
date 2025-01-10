package com.unitip.mobile.features.example.presentation.states

import com.unitip.mobile.features.example.domain.models.User

data class ExampleUsersState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data class Success(val users: List<User>) : Detail
        data class Failure(
            val message: String,
            val code: Int = 0
        ) : Detail
    }
}
