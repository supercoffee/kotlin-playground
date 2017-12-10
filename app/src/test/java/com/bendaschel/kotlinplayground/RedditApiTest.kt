package com.bendaschel.kotlinplayground

import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before

import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

const val CLIENT_ID = "-0g6rsJ5dyIIgQ"
const val GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client"

class RedditApiTest {
    private lateinit var redditApi: RedditApi

    @Before
    fun setUp() {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor{
                    val creds = Credentials.basic(CLIENT_ID, "nopassword")
                    val request = it.request().newBuilder().header("Authorization", creds).build()
                    it.proceed(request)
                }
                .build()
        redditApi = Retrofit.Builder()
                .client(client)
                .baseUrl("https://www.reddit.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi::class.java)
    }

    @Test
    fun testClientAuth() {
        val deviceId = UUID.randomUUID().toString()
        val call = redditApi.accessToken(GRANT_TYPE, deviceId)
        val response = call.execute()
        assertThat(response.body()?.token_type, `is`("bearer"))
    }

    @Test
    fun testSubreddit() {
        val call = redditApi.subreddit("aww")
        val response = call.execute().body()
        assertNotNull(response)
    }
}