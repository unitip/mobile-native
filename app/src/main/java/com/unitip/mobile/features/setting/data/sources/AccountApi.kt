package com.unitip.mobile.features.setting.data.sources

import com.unitip.mobile.features.setting.data.dtos.EditPayload
import com.unitip.mobile.features.setting.data.dtos.EditResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface AccountApi {
    @PATCH("accounts")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body payload: EditPayload
    ): Response<EditResponse>
}