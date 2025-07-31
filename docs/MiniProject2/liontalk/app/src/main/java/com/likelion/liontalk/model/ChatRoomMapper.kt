package com.likelion.liontalk.model

import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.data.remote.dto.ChatRoomDto

object ChatRoomMapper {
    fun ChatRoomDto.toEntity() = ChatRoomEntity(
        id,
        title,
        owner,
        users,
        0,
        "",
        lastReadMessageTimestamp = 0L,
        isLocked,
        createdAt
    )

    fun ChatRoomDto.toModel() = ChatRoom(
        id,
        title,
        owner,
        users,
        0,
        "",
        isLocked,
        createdAt
    )

    fun ChatRoomEntity.toDto() = ChatRoomDto(
        id,
        title,
        owner,
        users,
        isLocked,
        createdAt
    )

    fun ChatRoomEntity.toModel() = ChatRoom(
        id,
        title,
        owner,
        users,
        unReadCount,
        lastReadMessageId,
        isLocked,
        createdAt
    )

}