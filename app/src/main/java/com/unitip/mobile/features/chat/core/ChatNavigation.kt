package com.unitip.mobile.features.chat.core

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unitip.mobile.features.chat.presentation.screens.ConversationScreen

fun NavGraphBuilder.chatNavigation() {
    composable<ChatRoutes.Conversation> { ConversationScreen() }
}