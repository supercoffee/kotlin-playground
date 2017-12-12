package com.bendaschel.kotlinplayground

import android.app.Activity

val Activity.redditApi: RedditApi
    get() = retrofit2.Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(com.bendaschel.kotlinplayground.RedditApi::class.java)