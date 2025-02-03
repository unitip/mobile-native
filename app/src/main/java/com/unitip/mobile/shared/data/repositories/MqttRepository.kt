package com.unitip.mobile.shared.data.repositories

import android.util.Log
import com.unitip.mobile.shared.commons.configs.MqttTopics
import com.unitip.mobile.shared.commons.extensions.isDriver
import com.unitip.mobile.shared.data.managers.SessionManager
import com.unitip.mobile.shared.data.providers.MqttProvider
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttRepository @Inject constructor(
    private val sessionManager: SessionManager,
    mqttProvider: MqttProvider
) {
    companion object {
        private const val TAG = "MqttRepository"
    }

    private val client: MqttAndroidClient = mqttProvider.client

    fun initialize() {
        /**
         * mendapatkan session dari user yang sedang login saat ini
         */
        val session = sessionManager.read()

        /**
         * inisialisasi beberapa topics mqtt yang akan digunakan
         */
        val publishDriverOnlineStatusTopic = MqttTopics.Dashboard.publishDriverOnlineStatus(
            driverUserId = session.id
        )

        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = false

            /**
             * ketika user login sebagai driver, maka status online dan offline
             * dari user tersebut akan dicatat dan disimpan pada broker mqtt
             */
            if (session.isDriver())
                setWill(
                    publishDriverOnlineStatusTopic,
                    "offline".toByteArray(),
                    2,
                    true
                )

            /**
             * setiap user yang login dapat mengirimkan pesan dan memiliki status
             * typing, sehingga perlu mereset status typing tersebut dari broker
             * ketika user kehilangan koneksi
             */
//                setWill(
//                    MqttTopics.Chats.publishTypingStatus(currentUserId = session.id),
//                    "".toByteArray(), // untuk menghapus retained, maka kirim empty string
//                    2,
//                    true
//                )
        }

        client.connect(
            options = options,
            userContext = null,
            callback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "onSuccess: connected")

                    /**
                     * ketika user login sebagai driver, maka status online dan offline
                     * dari user tersebut akan dicatat dan disimpan pada broker mqtt
                     */
                    if (session.isDriver())
                        client.publish(
                            publishDriverOnlineStatusTopic,
                            "online".toByteArray(),
                            2,
                            true
                        )
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    exception?.printStackTrace()
                    Log.d(TAG, "onFailure: ${exception?.message ?: "unknown"}")
                }
            })
    }
}