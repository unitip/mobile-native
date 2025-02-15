package com.unitip.mobile.features.chat.data.repositories

import android.util.Log
import com.google.gson.Gson
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeChat
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.features.chat.domain.models.ReadCheckpoint
import com.unitip.mobile.shared.commons.configs.MqttTopics
import com.unitip.mobile.shared.data.providers.MqttProvider
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealtimeConversationRepository @Inject constructor(
    mqttProvider: MqttProvider
) {
    companion object {
        private const val TAG = "RealtimeChatRepository"
    }

    private val client: MqttAndroidClient = mqttProvider.client
    private val gson = Gson()

    // listeners
    private var messageListener: RealtimeChat.MessageListener? = null
    private var typingStatusListener: RealtimeChat.TypingStatusListener? = null
    private var readCheckpointListener: RealtimeChat.ReadCheckpointListener? = null

    // topics
    private lateinit var publishSubscribeMessageTopic: String
    private lateinit var publishTypingStatusTopic: String
    private lateinit var subscribeTypingStatusTopic: String
    private lateinit var publishSubscribeReadCheckpointTopic: String

    fun openConnection(
        roomId: String,
        currentUserId: String,
        otherUserId: String
    ) {
        publishSubscribeMessageTopic = MqttTopics.Chats.publishSubscribeMessage(
            roomId = roomId
        )
        publishTypingStatusTopic = MqttTopics.Chats.publishTypingStatus(
            currentUserId = currentUserId
        )
        subscribeTypingStatusTopic = MqttTopics.Chats.subscribeTypingStatus(
            otherUserId = otherUserId
        )
        publishSubscribeReadCheckpointTopic = MqttTopics.Chats.publishSubscribeReadCheckpoint(
            roomId = roomId
        )

        subscribeToTopics(
            roomId = roomId,
            currentUserId = currentUserId
        )

        client.setCallback(object : MqttCallbackExtended {
            override fun messageArrived(topic: String?, message: MqttMessage?) = Unit
            override fun deliveryComplete(token: IMqttDeliveryToken?) = Unit
            override fun connectionLost(cause: Throwable?) =
                unsubscribeFromTopics()

            override fun connectComplete(reconnect: Boolean, serverURI: String?) =
                subscribeToTopics(
                    roomId = roomId,
                    currentUserId = currentUserId
                )
        })
    }

    fun unsubscribeFromTopics() {
        Log.d(TAG, "unsubscribeFromTopics")
        if (client.isConnected) {
            client.unsubscribe(publishSubscribeMessageTopic)
            client.unsubscribe(subscribeTypingStatusTopic)
            client.unsubscribe(publishSubscribeReadCheckpointTopic)
        }
    }

    private fun subscribeToTopics(
        roomId: String,
        currentUserId: String
    ) {
        /**
         * subscribe ke beberapa topic berikut:
         * - messages
         * - typing status
         * - read checkpoint
         */
        if (client.isConnected) {
            client.subscribe(publishSubscribeMessageTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[message subscribe] $payload")
                if (payload.isNotBlank() && messageListener != null) {
                    val receivedMessage = gson.fromJson(payload, Message::class.java)
                    if (receivedMessage.userId != currentUserId)
                        messageListener!!.onMessageReceived(
                            message = receivedMessage
                        )
                }
            }

            client.subscribe(subscribeTypingStatusTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[typing status subscribe] $payload")
                if (typingStatusListener != null)
                    typingStatusListener!!.onTypingStatusReceived(
                        isTyping = payload == roomId
                    )
            }

            client.subscribe(publishSubscribeReadCheckpointTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[read checkpoint subscribe] $payload")
                if (payload.isNotBlank() && readCheckpointListener != null) {
                    val received = gson.fromJson(payload, ReadCheckpoint::class.java)
                    if (received.userId != currentUserId)
                        readCheckpointListener!!.onReadCheckpointReceived(
                            readCheckpoint = received
                        )
                }
            }
        }
    }

    fun listenMessages(listener: RealtimeChat.MessageListener) {
        messageListener = listener
    }

    fun listenTypingStatus(listener: RealtimeChat.TypingStatusListener) {
        typingStatusListener = listener
    }

    fun listenReadCheckpoint(listener: RealtimeChat.ReadCheckpointListener) {
        readCheckpointListener = listener
    }

    fun notifyMessage(message: Message) {
        if (client.isConnected) {
            client.publish(
                topic = publishSubscribeMessageTopic,
                payload = gson.toJson(message).toByteArray(),
                qos = 2,
                retained = false
            )
        }
    }

    fun notifyTypingStatus(roomId: String) {
        /**
         * update ke depan:
         * typing status perlu dihapus melalui onWillTopic untuk mengantisipasi ketika
         * user keluar dari aplikasi atau kehilangan sinyal
         */
        if (client.isConnected) {
            client.publish(
                topic = publishTypingStatusTopic,
                payload = roomId.toByteArray(),
                qos = 2,
                retained = true
            )
        }
    }

    fun notifyReadCheckpoint(readCheckpoint: ReadCheckpoint) {
        if (client.isConnected)
            client.publish(
                topic = publishSubscribeReadCheckpointTopic,
                payload = gson.toJson(readCheckpoint).toByteArray(),
                qos = 2,
                retained = false
            )
    }
}