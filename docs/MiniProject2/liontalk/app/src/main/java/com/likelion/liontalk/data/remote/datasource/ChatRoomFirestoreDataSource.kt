package com.likelion.liontalk.data.remote.datasource

import android.util.Log
import androidx.compose.animation.core.snap
import androidx.room.util.query
import com.google.firebase.Firebase
import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChatRoomFirestoreDataSource {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("chatrooms")

    suspend fun fetchRooms(): List<ChatRoomDto> {
        try {
            val snapshot = collection.get().await()

            Log.d("ChatRoomfirestoreDataSource", "총 문서 수: ${snapshot.size()}")

            for (doc in snapshot.documents) {
                Log.d("ChatRoomfirestoreDataSource", "id=${doc.id}, data=${doc.data}")
            }

            return snapshot.documents.mapNotNull { doc ->
                doc.toObject(ChatRoomDto::class.java)?.copy(
                    id = doc.id // Firestore 문서 id를 ChatRoomDto.id에 넣어줌
                )
            }
        }  catch (e:Exception) {
            Log.e("ChatRoomFirestoreDataSource", "${e.message}")
            throw Exception("서버 채팅방 목록 조회 실패:${e.message}}")
        }

    }

    suspend fun fetchRoom(id: String):ChatRoomDto? {
        Log.d("ChatRoomfirestoreDataSource", "id:$id")
        try {
            val snapshot = collection.whereEqualTo("id",id)
                .limit(1)
                .get()
                .await()

            val doc = snapshot.documents.firstOrNull() ?: throw Exception("해당 채팅방 없음.")

            return doc.toObject(ChatRoomDto::class.java)?.copy(
                id = doc.id // Firestore 문서 id를 ChatRoomDto.id에 넣어줌
            )
        } catch (e:Exception) {
            throw Exception("서버 채팅방 조회 실패:${e.message}}")
        }
    }

    suspend fun updateRoom(dto: ChatRoomDto):ChatRoomDto {
        try {
            val querySanpshot = collection.whereEqualTo("id", dto.id).limit(1).get().await()
            val doc = querySanpshot.documents.firstOrNull() ?: throw Exception("채팅방 없음")

            collection.document(doc.id).set(dto).await()
            return dto
        }   catch (e:Exception) {
            Log.e("ChatRoomFirestoreDataSource", "${e.message}")
            throw Exception("서버 채팅방 변경 실패:${e.message}}")
        }
    }

    suspend fun createRoom(chatRoomDto: ChatRoomDto): ChatRoomDto {

        try {
            val docRef = db.collection("chatrooms")
                .add(chatRoomDto)
                .await()

            Log.d("ChatRoomFirestoreDataSource", "$docRef")
            val id = docRef.id
            docRef.update("id",id).await()

            return chatRoomDto.copy(id = id)
        } catch (e:Exception) {
            Log.e("ChatRoomFirestoreDataSource", "${e.message}")
            throw Exception("서버 채팅방 생성 실패:${e.message}}")
        }
    }

    suspend fun deleteRoom(id: String) {
        try {
            val querySnapshot = collection.whereEqualTo("id", id).limit(1).get().await()
            val doc = querySnapshot.documents.firstOrNull() ?: throw Exception("삭제할 채팅방 없음")

            collection.document(doc.id).delete().await()
        }  catch (e:Exception) {
            Log.e("ChatRoomFirestoreDataSource", "${e.message}")
            throw Exception("서버 채팅방 삭제 실패:${e.message}}")
        }
    }
}