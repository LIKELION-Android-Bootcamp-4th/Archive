package com.likelion.liontalk.features.sign

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.likelion.liontalk.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignViewModel(application: Application, private val authRepository: AuthRepository) : AndroidViewModel(application){
    fun kakaoLogin(navController: NavController) {
        viewModelScope.launch {
            try {
                authRepository.kakaoLogin()
                navController.navigate("chatroom_list") {
                    popUpTo("sign") { inclusive = true }
                }
            } catch (e: Exception) {
                Log.e("Auth", "카카오 로그인 실패", e)
            }
        }
    }

    fun naverLogin(navController: NavController) {
        viewModelScope.launch {
            try {
                authRepository.naverLogin()
                navController.navigate("chatroom_list") {
                    popUpTo("sign") { inclusive = true }
                }
            } catch (e: Exception) {
                Log.e("Auth", "네이버 로그인 실패", e)
            }
        }
    }

    fun googleLogin(
        context: Context,
        launcher: ActivityResultLauncher<Intent>
    ) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("359936561333-compute@developer.gserviceaccount.com") // Firebase 콘솔에서 발급한 Web client ID
            .requestEmail()
            .build()

        val client: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(client.signInIntent)
    }

    fun handleGoogleSignInResult(data: Intent, navController: NavController) {
        viewModelScope.launch {
            try {
                authRepository.handleGoogleSignInResult(data)

                Log.d("구글","구글 로그인 성공")

                navController.navigate("chatroom_list") {
                    popUpTo("sign") { inclusive = true }
                }

            } catch (e: Exception) {
                Log.e("Auth", "구글 로그인 실패", e)
            }
        }
    }
}