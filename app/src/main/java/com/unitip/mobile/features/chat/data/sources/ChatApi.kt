package com.unitip.mobile.features.chat.data.sources

import com.unitip.mobile.features.chat.data.dtos.GetAllRoomsResponse
import com.unitip.mobile.features.chat.data.dtos.SendMessagePayload
import com.unitip.mobile.features.chat.data.dtos.SendMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApi {
    @POST("chats")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body payload: SendMessagePayload
    ): Response<SendMessageResponse>

    @GET("chats/rooms/users")
    suspend fun getAllRooms(
        @Header("Authorization") token: String
    ): Response<GetAllRoomsResponse>
}