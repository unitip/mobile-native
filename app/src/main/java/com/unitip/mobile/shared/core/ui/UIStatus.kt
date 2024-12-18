package com.unitip.mobile.shared.core.ui

@Deprecated("Use data class instead with sealed interface")
enum class UIStatus {
    Initial,
    Loading,
    Success,
    Failure,
}