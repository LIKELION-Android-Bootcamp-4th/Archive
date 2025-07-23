package com.likelion.liontalk.model

import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.dto.ChatMessageDto

object ChatMessageMapper {
    fun ChatMessageDto.toEntity() = ChatMessageEntity(id,roomId,sender,content,createdAt)
    fun ChatMessageEntity.toDto() = ChatMessageDto(id,roomId,sender,content,createdAt)
    fun ChatMessageDto.toModel() = ChatMessage(id,roomId,sender,content,"text", createdAt)
    fun ChatMessageEntity.toModel() = ChatMessage(id,roomId,sender,content,"text", createdAt)
    fun ChatMessage.toEntity() = ChatMessageEntity(id,roomId,sender,content,createdAt)
    fun ChatMessage.toDto() = ChatMessageDto(id,roomId,sender,content,createdAt)
}