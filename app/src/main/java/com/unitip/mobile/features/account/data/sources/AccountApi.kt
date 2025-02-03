package com.unitip.mobile.features.account.data.sources

import com.unitip.mobile.features.account.data.dtos.ChangeRolePayload
import com.unitip.mobile.features.account.data.dtos.ChangeRoleResponse
import com.unitip.mobile.features.account.data.dtos.EditPasswordPayload
import com.unitip.mobile.features.account.data.dtos.EditPasswordResponse
import com.unitip.mobile.features.account.data.dtos.EditPayload
import com.unitip.mobile.features.account.data.dtos.EditResponse
import com.unitip.mobile.features.account.data.dtos.GetOrderHistoriesResponse
import com.unitip.mobile.features.account.data.dtos.GetRoleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface AccountApi {
    @GET("accounts/driver/orders/histories")
    suspend fun getOrderHistories(
        @Header("Authorization") token: String
    ): Response<GetOrderHistoriesResponse>

    @GET("accounts/profile/roles")
    suspend fun getRole(
        @Header("Authorization") token: String
    ): Response<GetRoleResponse>

    @PATCH("accounts/profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body payload: EditPayload
    ): Response<EditResponse>

    @PATCH("accounts/profile/password")
    suspend fun editPassword(
        @Header("Authorization") token: String,
        @Body payload: EditPasswordPayload
    ): Response<EditPasswordResponse>

    @PATCH("accounts/profile/roles")
    suspend fun changeRole(
        @Header("Authorization") token: String,
        @Body payload: ChangeRolePayload
    ): Response<ChangeRoleResponse>
}