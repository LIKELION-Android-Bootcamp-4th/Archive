package com.likelion.liontalk.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatUser(val name : String, val avataUrl:String?)