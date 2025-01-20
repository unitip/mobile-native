package com.unitip.mobile.features.chat.data.repositories

import arrow.core.Either
import com.unitip.mobile.features.chat.data.dtos.SendMessagePayload
import com.unitip.mobile.features.chat.data.dtos.SendMessageResponse
import com.unitip.mobile.features.chat.data.sources.ChatApi
import com.unitip.mobile.features.chat.domain.models.GetAllMessagesResult
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.domain.models.OtherUser
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
                        lastMessage = it.lastMessage,
                        lastSentUserId = it.lastSentUserId,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                        otherUser = Room.OtherUser(
                            id = it.otherUser.id,
                            name = it.otherUser.name
                        )
                    )
                })

                false -> Either.Left(response.mapToFailure())
            }
        } catch (e: Exception) {
            return Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
        }
    }

    suspend fun sendMessage(
        roomId: String,
        id: String,
        message: String
    ): Either<Failure, SendMessageResponse> = try {
        val token = sessionManager.read()?.token
        val response = chatApi.sendMessage(
            token = "Bearer $token",
            roomId = roomId,
            payload = SendMessagePayload(
                id = id,
                message = message,
            )
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(
                SendMessageResponse(
                    id = result.id,
                    message = result.message,
                    createdAt = result.createdAt,
                    updatedAt = result.updatedAt
                )
            )

            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }

    suspend fun getAllMessages(
        roomId: String
    ): Either<Failure, GetAllMessagesResult> = try {
        val token = sessionManager.read()?.token
        val response = chatApi.getAllMessages(
            token = "Bearer $token",
            roomId = roomId
        )
        val result = response.body()

        when (response.isSuccessful && result != null) {
            true -> Either.Right(GetAllMessagesResult(
                otherUser = OtherUser(
                    id = result.otherUser.id,
                    lastReadMessageId = result.otherUser.lastReadMessageId
                ),
                messages = result.messages.map {
                    Message(
                        id = it.id,
                        message = it.message,
                        isDeleted = it.isDeleted,
                        roomId = it.roomId,
                        userId = it.userId,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
            ))

            false -> Either.Left(response.mapToFailure())
        }
    } catch (e: Exception) {
        Either.Left(Failure(message = "Terjadi kesalahan tak terduga!"))
    }
}