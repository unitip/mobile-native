package com.unitip.mobile.features.chat.data.repositories

import android.util.Log
import com.google.gson.Gson
import com.unitip.mobile.BuildConfig
import com.unitip.mobile.features.chat.domain.callbacks.RealtimeChat
import com.unitip.mobile.features.chat.domain.models.Message
import com.unitip.mobile.shared.data.providers.MqttProvider
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealtimeChatRepository @Inject constructor(
    mqttProvider: MqttProvider
) {
    companion object {
        private const val TAG = "RealtimeChatRepository"
    }

    private val client: MqttAndroidClient = mqttProvider.client
    private val gson = Gson()
    private var messageListener: RealtimeChat.MessageListener? = null
    private var typingStatusListener: RealtimeChat.TypingStatusListener? = null

    // topics
    private val topicPrefix = "com.unitip/${BuildConfig.MQTT_SECRET}/chat"
    private lateinit var messagePubTopic: String
    private lateinit var messageSubTopic: String
    private lateinit var typingStatusPubTopic: String
    private lateinit var typingStatusSubTopic: String

    fun openConnection(
        currentUserId: String,
        otherUserId: String
    ) {
        messagePubTopic = "$topicPrefix/message/$otherUserId-$currentUserId"
        messageSubTopic = "$topicPrefix/message/$currentUserId-$otherUserId"
        typingStatusPubTopic = "$topicPrefix/typing-status/$otherUserId-$currentUserId"
        typingStatusSubTopic = "$topicPrefix/typing-status/$currentUserId-$otherUserId"

        subscribeToTopics()

        client.setCallback(object : MqttCallbackExtended {
            override fun messageArrived(topic: String?, message: MqttMessage?) = Unit
            override fun deliveryComplete(token: IMqttDeliveryToken?) = Unit
            override fun connectionLost(cause: Throwable?) = unsubscribeFromTopics()
            override fun connectComplete(reconnect: Boolean, serverURI: String?) =
                subscribeToTopics()
        })
    }

    fun unsubscribeFromTopics() {
        if (client.isConnected) {
            client.unsubscribe(messageSubTopic)
            client.unsubscribe(typingStatusSubTopic)
        }
    }

    private fun subscribeToTopics() {
        /**
         * subscribe ke beberapa topic berikut:
         * - messages
         * - typing status
         */
        if (client.isConnected) {
            client.subscribe(messageSubTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[message subscribe] $payload")
                if (payload.isNotEmpty() && messageListener != null)
                    messageListener!!.onMessageReceived(
                        message = gson.fromJson(payload, Message::class.java)
                    )
            }

            client.subscribe(typingStatusSubTopic, 2) { _, message ->
                val payload = message.toString()
                Log.d(TAG, "[typing status subscribe] $payload")
                if (payload.isNotEmpty() && typingStatusListener != null)
                    typingStatusListener!!.onTypingStatusReceived(
                        isTyping = payload == "true"
                    )
            }
        }
    }

    fun listenMessageFromOther(listener: RealtimeChat.MessageListener) {
        this.messageListener = listener
    }

    fun listenTypingStatusFromOther(listener: RealtimeChat.TypingStatusListener) {
        this.typingStatusListener = listener
    }

    fun notifyMessageToOther(message: Message) {
        if (client.isConnected) {
            client.publish(
                topic = messagePubTopic,
                payload = gson.toJson(message).toByteArray(),
                qos = 2,
                retained = false
            )
        }
    }

    fun notifyTypingStatusToOther(isTyping: Boolean) {
        /**
         * update ke depan:
         * typing status perlu dihapus melalui onWillTopic untuk mengantisipasi ketika
         * user keluar dari aplikasi atau kehilangan sinyal
         */
        if (client.isConnected) {
            client.publish(
                topic = typingStatusPubTopic,
                payload = isTyping.toString().toByteArray(),
                qos = 2,
                retained = true
            )
        }
    }
}