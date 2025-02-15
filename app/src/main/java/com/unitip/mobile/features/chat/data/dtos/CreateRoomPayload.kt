package com.unitip.mobile.features.chat.data.dtos

//{
//  "members": [
//    "user_uuid_1",
//    "user_uuid_2"
//  ]
//}
data class CreateRoomPayload(
    val members: List<String>
){
    data class Member(
        val id: String
    )
}


