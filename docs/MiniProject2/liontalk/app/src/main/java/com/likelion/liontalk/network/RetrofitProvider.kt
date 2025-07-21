package com.likelion.liontalk.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://68704d357ca4d06b34b67474.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun <T> create(service: Class<T>): T = retrofit.create(service)
}