package com.likelion.liontalk.model

import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.dto.ChatMessageDto

object ChatMessageMapper {
    fun ChatMessageDto.toEntity() = ChatMessageEntity(id,roomId,sender,content,createdAt)
    fun ChatMessageEntity.toDto() = ChatMessageDto(id,roomId,sender,content,createdAt)
}