package com.me.cakeapp

import com.me.cakeapp.api.CakesAPI
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class CakeTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CakesAPI::class.java)


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCakeList() {
        val body = javaClass.classLoader?.getResourceAsStream("cakes.json")?.source()?.buffer()
            ?.readString(StandardCharsets.UTF_8)

        val response = MockResponse().setResponseCode(200).setBody(body!!)
        mockWebServer.enqueue(response)

        val actual = api.testCakes().execute()
        assertEquals(response.toString().contains("200"), actual.code().toString().contains("200"))
    }

}