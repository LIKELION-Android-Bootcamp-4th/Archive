package com.likelion.liontalk.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import kotlinx.coroutines.tasks.await

class ChatMessageFirestoreDataSource {
    private val db = FirebaseFirestore.getInstance()

    suspend fun sendMessage(dto: ChatMessageDto): ChatMessageDto? {
        val roomId = dto.roomId.toString()
        val messageCollection = db.collection("chatrooms")
            .document(roomId)
            .collection("messages")

        val docRef = messageCollection.add(dto).await()

        val id = docRef.id
        messageCollection.document(docRef.id).update("id",id).await()
        return dto.copy(id = id)
    }

    suspend fun fetchMessages():List<ChatMessageDto> {
        val roomsSnapshot = db.collection("chatrooms").get().await()

        val allMessages = mutableListOf<ChatMessageDto>()
        for (roomDoc in roomsSnapshot) {
            val messageSnapshot = roomDoc.reference.collection("messages")
                .orderBy("createdAt")
                .get()
                .await()
            allMessages += messageSnapshot.toObjects(ChatMessageDto::class.java)
        }
        return allMessages
    }

    suspend fun fetchMessagesByRoomId(roomId: String):List<ChatMessageDto> {
        val snapshot = db.collection("chatrooms")
            .document(roomId.toString())
            .collection("messages")
            .orderBy("createdAt")
            .get()
            .await()

        return snapshot.toObjects(ChatMessageDto::class.java)
    }
}