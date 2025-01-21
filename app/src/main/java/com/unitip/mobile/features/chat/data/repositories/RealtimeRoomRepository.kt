package com.unitip.mobile.features.chat.data.repositories

import android.util.Log
import com.google.gson.Gson
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeRoom
import com.unitip.mobile.features.chat.domain.models.Room
import com.unitip.mobile.shared.commons.configs.MqttTopics
import com.unitip.mobile.shared.data.providers.MqttProvider
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealtimeRoomRepository @Inject constructor(
    mqttProvider: MqttProvider
) {
    companion object {
        private const val TAG = "RealtimeRoomRepository"
    }

    private val client: MqttAndroidClient = mqttProvider.client
    private val gson = Gson()
    private var isConnectionEstablished = false

    // listeners
    private var listener: RealtimeRoom.Listener? = null

    // topics
    private lateinit var subscribeRoomsTopic: String

    fun openConnection(
        currentUserId: String
    ) {
        subscribeRoomsTopic = MqttTopics.Chats.subscribeRooms(
            currentUserId = currentUserId
        )

        subscribeToTopics()

        client.setCallback(object : MqttCallbackExtended {
            override fun messageArrived(topic: String?, message: MqttMessage?) = Unit
            override fun deliveryComplete(token: IMqttDeliveryToken?) = Unit
            override fun connectionLost(cause: Throwable?) = unsubscribeFromTopics()
            override fun connectComplete(reconnect: Boolean, serverURI: String?) =
                subscribeToTopics()
        })

        isConnectionEstablished = true
    }

    fun unsubscribeFromTopics() {
        if (client.isConnected) {
            client.unsubscribe(subscribeRoomsTopic)
        }
    }

    private fun subscribeToTopics() {
        if (client.isConnected) {
            client.subscribe(subscribeRoomsTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[room subscribe] $payload")
                if (payload.isNotBlank() && listener != null)
                    listener!!.onRoomReceived(
                        room = gson.fromJson(payload, Room::class.java)
                    )
            }
        }
    }

    fun listen(listener: RealtimeRoom.Listener) {
        this.listener = listener
    }

    fun notify(
        otherUserId: String,
        room: Room
    ) {
        Log.d(TAG, "notify: called")
        if (client.isConnected && isConnectionEstablished) {
            client.publish(
                topic = MqttTopics.Chats.publishRoom(
                    otherUserId = otherUserId
                ),
                payload = gson.toJson(room).toByteArray(),
                qos = 2,
                retained = false
            )
        }
    }
}