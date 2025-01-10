package com.unitip.mobile.features.example.data.dtos

data class GetAllUsersResponse(
    val success: Boolean,
    val users: List<User>
) {
    data class User(
        val id: String,
        val name: String,
        val age: Int
    )
}
