package com.unitip.mobile.features.activity.presentation.state

data class CreateActivityState(
    val detail: Detail = Detail.Initial
) {
    sealed interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Failure(
            val message: String
        ) : Detail
    }
}
