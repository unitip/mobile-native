package com.unitip.mobile.features.chat.commons

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unitip.mobile.features.chat.presentation.screens.ConversationScreen

fun NavGraphBuilder.chatNavigation() {
    composable<ChatRoutes.Conversation> {
        val data: ChatRoutes.Conversation = it.toRoute()
        ConversationScreen(
            roomId = data.roomId,
            otherUserName = data.otherUserName
        )
    }
}