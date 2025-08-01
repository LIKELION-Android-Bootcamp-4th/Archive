package com.likelion.liontalk.data.remote.datasource

import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class KakaoDataSource(private val context:Context) {
    suspend fun signIn(): String = suspendCancellableCoroutine { cont ->
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "에러: ${error.message}", error)
                cont.resumeWithException(error)
            } else if (token != null) {
                cont.resume(token.accessToken, null)
            } else {
                cont.resumeWithException(Exception("Kakao login failed: unknown error"))
            }
        }
    }
}