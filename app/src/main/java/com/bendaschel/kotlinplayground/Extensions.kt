package com.bendaschel.kotlinplayground

import android.app.Activity
import android.graphics.Color
import android.support.v7.graphics.Palette

val Activity.redditApi: RedditApi
    get() = retrofit2.Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(com.bendaschel.kotlinplayground.RedditApi::class.java)

val Palette.preferredColor: Int
    get() {
        val methodOrder: List<(Int) -> Int> = listOf(
                this::getDarkVibrantColor,
                this::getDarkMutedColor,
                this::getLightVibrantColor,
                this::getLightMutedColor,
                this::getDominantColor
        )
        var bestColor: Int = Color.WHITE

        methodOrder.forEach {
            bestColor = it(bestColor)
        }

        return bestColor
    }