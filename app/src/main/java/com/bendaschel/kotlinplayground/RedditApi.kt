package com.bendaschel.kotlinplayground

import retrofit2.Call
import retrofit2.http.*

interface RedditApi {

    @POST("/api/v1/access_token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun accessToken(@Query("grant_type") grantType: String = "https://oauth.reddit.com/grants/installed_client",
                    @Query("device_id") deviceId: String): Call<AuthResponse>
}