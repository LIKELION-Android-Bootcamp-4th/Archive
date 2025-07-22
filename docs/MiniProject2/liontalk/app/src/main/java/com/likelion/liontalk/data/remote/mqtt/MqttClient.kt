package com.likelion.liontalk.data.remote.mqtt

import android.util.Log
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
import com.hivemq.client.mqtt.MqttClient
import java.lang.Error
import java.nio.charset.StandardCharsets
import java.util.UUID

//HiveMQ
object MqttClient {
    private val mqttClient : Mqtt3BlockingClient = MqttClient.builder()
        .useMqttVersion3()  //Mqtt 3.1.1
        .identifier("liontalk_${UUID.randomUUID()}")
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .buildBlocking()

    //현재 연결 여부
    private var isConnected = false

    //현재 구독중인 토픽들
    private val subscribedTopics = mutableSetOf<String>()
    fun connect(onConnected: (() -> Unit)? = null, onError: ((Throwable) -> Unit)? = null) {
        if (isConnected) {
            onConnected?.invoke()
            return
        }
        try {
            mqttClient.connect()
            isConnected = true
            Log.d("MQTT","MQTT 연결 성공")
            onConnected?.invoke()
        } catch (e: Exception) {
            Log.e("MQTT","MQTT 연결 실패:${e.message}")
            onError?.invoke(e)
        }
    }

    // 메세지 수신시 호출될 외부 콜백 함수
    private var messageCallback: ((topic: String, message: String) -> Unit)? = null

    // 메세지 수신 콜백 등록 함수
    fun setOnMessageReceived(callback: (topic: String, message: String) -> Unit) {
        messageCallback = callback
    }

    fun subscribe(topic : String) {
        if (!isConnected) {
            Log.e("MQTT","MQTT 연결 안됨 - subscribe 실패")
        }
        if(subscribedTopics.contains(topic)) {
            Log.d("MQTT","이미 구독중:$topic")
            return
        }
        mqttClient.toAsync().subscribeWith()
            .topicFilter(topic)
            .callback { publish ->
                //수신 메세지 처리
                val receivedTopic = publish.topic.toString()
                val payloadBuffer = publish.payload.orElse(null)

                val message = payloadBuffer?.let { buffer ->
                    val readOnlyBuffer = buffer.asReadOnlyBuffer()
                    val bytes = ByteArray(readOnlyBuffer.remaining())
                    readOnlyBuffer.get(bytes)
                    String(bytes , StandardCharsets.UTF_8)
                } ?: ""
                Log.d("MQTT", "수신 : [$receivedTopic] $message")
                // 콜백 호출
                messageCallback?.invoke(receivedTopic,message)
            }
            .send()
        subscribedTopics.add(topic)
        Log.d("MQTT","구독 시작:$topic")
    }

    // 특정 topic 구독 해제
    fun unsubscribe(topic: String) {
        if (subscribedTopics.contains(topic)) {
            mqttClient.toAsync().unsubscribeWith()
                .topicFilter(topic)
                .send()

            subscribedTopics.remove(topic)
            Log.d("MQTT", "구독 해제:$topic")

        }
    }

    // 전체 topic 구독 해제
    fun unsubscriveAll() {
        for (topic in subscribedTopics.toList()) {
            unsubscribe(topic)
        }
    }
    // 특정 topic에 메세지를 전송한다.
    fun publish(topic: String, message: String) {
        if (!isConnected) {
            Log.e("MQTT","MQTT 연결 안됨 - publish 실패")
        }

        mqttClient.publishWith()
            .topic(topic)
            .payload(message.toByteArray(StandardCharsets.UTF_8))
            .send()

        Log.d("MQTT", "published:[$topic] $message")
    }

    fun disconnect() {
        if(isConnected) {
            mqttClient.disconnect()
            isConnected = false
            subscribedTopics.clear()
            Log.d("MQTT","MQTT 연결 해제")
        }
    }
}