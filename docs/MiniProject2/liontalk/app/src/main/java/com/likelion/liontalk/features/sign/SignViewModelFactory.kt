package com.likelion.liontalk.features.sign

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.likelion.liontalk.data.repository.AuthRepository

class SignViewModelFactory(
    private val application: Application,
    private val authRepository: AuthRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignViewModel(application, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}