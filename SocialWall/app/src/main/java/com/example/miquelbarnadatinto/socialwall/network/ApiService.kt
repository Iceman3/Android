package com.example.sergisanchezsolares.twitchcompanion.network

import com.example.miquelbarnadatinto.socialwall.model.TWStreamResponse
import com.example.miquelbarnadatinto.socialwall.model.TWUserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService{

    @Headers("Client-ID: diinlavlj8b4mi37x5wfzdlsbw982x")

    @GET("streams")
    fun getStreams(): Call<TWStreamResponse>

    @Headers("Client-ID: diinlavlj8b4mi37x5wfzdlsbw982x")
    @GET("users")
    fun getUsers(): Call<TWUserResponse>

    companion object {
        private var retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ApiService>(ApiService::class.java)
    }



}