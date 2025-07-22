package com.likelion.liontalk.features.setting

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.liontalk.data.repository.UserPreferenceRepository
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    var userName by mutableStateOf("")
    var avatarUrl by mutableStateOf("")

    private val userPreferenceRepository = UserPreferenceRepository.getInstance()

    val me: ChatUser? get() = userPreferenceRepository.meOrNull

    init {
        viewModelScope.launch {
            userPreferenceRepository.loadUserFromStorage()

            loadProfile()
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            userPreferenceRepository.setUser(ChatUser(userName,avatarUrl))
        }
    }
    fun loadProfile() {
        val user = me
        if(user != null) {
            userName = user.name
            avatarUrl = user.avataUrl.toString()
        }
    }
}