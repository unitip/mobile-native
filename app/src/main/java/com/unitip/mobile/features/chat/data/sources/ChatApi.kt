package com.unitip.mobile.features.chat.data.sources

import com.unitip.mobile.features.chat.data.dtos.GetAllRoomsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ChatApi {
    @GET("chats/rooms/users")
    suspend fun getAllRooms(
        @Header("Authorization") token: String
    ): Response<GetAllRoomsResponse>
}