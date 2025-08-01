package com.likelion.liontalk.data.remote.datasource

import android.content.Context
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class FirebaseAuthDataSource() {

    private val auth = Firebase.auth
    private val functions = Firebase.functions

    fun signInWithGoogleCredential(idToken: String) =
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))

    fun signInWithCustomToken(token: String) =
        auth.signInWithCustomToken(token)

    fun requestCustomToken(functionName: String, accessToken: String) =
        functions.getHttpsCallable(functionName)
            .call(mapOf("accessToken" to accessToken))
}