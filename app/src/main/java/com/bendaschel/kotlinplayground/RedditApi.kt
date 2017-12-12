package com.bendaschel.kotlinplayground

import retrofit2.Call
import retrofit2.http.*

interface RedditApi {

    @POST("/api/v1/access_token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun accessToken(@Query("grant_type") grantType: String = "https://oauth.reddit.com/grants/installed_client",
                    @Query("device_id") deviceId: String): Call<AuthResponse>

    @GET("/r/{subreddit}.json")
    fun subreddit(@Path("subreddit") name: String): Call<ListingWrapper>


    @GET("/comments/{threadId}/.json")
    fun comments(@Path("threadId") name: String): Call<List<ListingWrapper>>
}