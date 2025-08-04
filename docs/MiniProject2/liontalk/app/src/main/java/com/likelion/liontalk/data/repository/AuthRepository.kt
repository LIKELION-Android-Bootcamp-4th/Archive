package com.likelion.liontalk.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.likelion.liontalk.data.remote.datasource.FirebaseAuthDataSource
import com.likelion.liontalk.data.remote.datasource.KakaoDataSource
import com.likelion.liontalk.data.remote.datasource.NaverDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

class AuthRepository(
    private val firebaseAuthDataSource : FirebaseAuthDataSource,
    private val kakaoDataSource : KakaoDataSource,
    private val naverDataSource : NaverDataSource) {

    suspend fun kakaoLogin(): Unit {
        val accessToken = kakaoDataSource.signIn()
        Log.d("AuthRepository","kakao token : $accessToken")
        signInWithCustomToken("kakaoCustomAuth", accessToken)
    }

    suspend fun naverLogin() {
        val accessToken = naverDataSource.signIn()
        Log.d("AuthRepository","naver token : $accessToken")
        signInWithCustomToken("naverCustomAuth", accessToken)
    }

    suspend fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.result
        val idToken = account.idToken ?: throw Exception("idToken is null")
        firebaseAuthDataSource.signInWithGoogleCredential(idToken).await()
    }

    private suspend fun signInWithCustomToken(functionName: String, accessToken: String) {
        val result = firebaseAuthDataSource.requestCustomToken(functionName, accessToken).await()
        val customToken = (result.data as Map<*, *>)["token"] as String

        Log.d("AuthRepository","signInWithCustomToken token : $customToken")
        try {
            val authResult = firebaseAuthDataSource.signInWithCustomToken(customToken).await()
            Log.d("AuthRepository","signInWithCustomToken success: ${authResult.user?.uid}")
        } catch (e: Exception) {
            Log.e("AuthRepository","signInWithCustomToken failed", e)
            throw e
        }
    }


}