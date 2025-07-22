package com.likelion.liontalk.data.repository

import android.content.Context
import android.util.Log
import com.likelion.liontalk.data.local.datasource.PreferenceDataStore
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class UserPreferenceRepository private constructor(private val context: Context) {
    private val _user = MutableStateFlow<ChatUser?>(null)
    val user: StateFlow<ChatUser?> = _user

    val meOrNull: ChatUser? get() = _user.value

    fun requireMe(): ChatUser = requireNotNull(_user.value)

    val isInitialized: Boolean get() = _user.value != null

    suspend fun loadUserFromStorage() {
        val name = PreferenceDataStore.getString(context,"USER_NAME").first()
        val avataUrl = PreferenceDataStore.getString(context,"AVATA_URL").first()

        Log.d("DS","$name , $avataUrl")

        if(!name.isNullOrBlank()) {
            _user.value = ChatUser(name,avataUrl)
        }
    }

    suspend fun setUser(user: ChatUser) {
        Log.d("DS",user.toString())
        PreferenceDataStore.setString(context, "USER_NAME",user.name)
        user.avataUrl?.let { PreferenceDataStore.setString(context,"AVATA_URL",it) }
    }

    companion object {
        private var _instance: UserPreferenceRepository? = null

        fun getInstance(context: Context):UserPreferenceRepository {
            return _instance ?: synchronized(this) {
                _instance ?: UserPreferenceRepository(context.applicationContext)
            }
        }
    }
}