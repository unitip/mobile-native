package com.unitip.mobile.features.chat.domain.callbacks

import com.unitip.mobile.features.chat.domain.models.Room

object RealtimeRoom {
    interface Listener {
        fun onRoomReceived(room: Room)
    }
}