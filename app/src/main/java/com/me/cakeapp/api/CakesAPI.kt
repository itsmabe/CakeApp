package com.me.cakeapp.api

import com.me.cakeapp.data.json.CakesDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CakesAPI {

    @GET("cakes")
    suspend fun getCakes(): CakesDTO

    @GET("cakes")
    fun testCakes(): Call<CakesDTO>


    companion object {
        fun create(): CakesAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://gist.githubusercontent.com/juananthony/c51c635c877d53d0fbc7d803f23af7ea/raw/0d4454a75859e8f94834a3de257b0b69a77e0b10/")
                .build()
            return retrofit.create(CakesAPI::class.java)
        }
    }
}