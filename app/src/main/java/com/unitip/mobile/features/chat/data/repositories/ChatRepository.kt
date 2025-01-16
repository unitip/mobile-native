package com.unitip.mobile.features.chat.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.chat.data.sources.ChatApi
import com.unitip.mobile.features.chat.domain.models.Room
import com.unitip.mobile.shared.commons.extensions.mapToFailure
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.domain.models.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatApi: ChatApi,
    private val sessionManager: SessionManager
) {
    suspend fun getAllRooms(): Either<Failure, List<Room>> {
        try {
            val token = sessionManager.read()?.token
            val response = chatApi.getAllRooms(token = "Bearer $token")
            val result = response.body()

            return when (response.isSuccessful && result != null) {
                true -> Either.Right(result.rooms.map {
                    Room(
                        id = it.id,
                        fromUserId = it.fromUserId,
                        fromUserName = it.fromUserName,
                        message = it.lastMessage,
                        lastSentUserId = it.lastSentUserId
                    )
                })

                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }
}