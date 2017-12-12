package com.bendaschel.kotlinplayground

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_thread.*
import org.jetbrains.anko.doAsyncResult
import android.R.attr.bitmap
import android.graphics.Color
import android.support.v7.graphics.Palette



class ThreadActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thread)

        val id = intent.getStringExtra("threadId")

        // TODO: load thread from reddit api and display it
        redditApi.comments(id).enqueue(object : Callback<List<ListingWrapper>> {
            override fun onFailure(call: Call<List<ListingWrapper>>?, t: Throwable?) {
                Log.e("ThreadActivity", "Failed to load thread", t)
            }

            override fun onResponse(call: Call<List<ListingWrapper>>?, response: Response<List<ListingWrapper>>?) {

                // TODO: Holy shit, simplify this
                val data = response?.body()!!.first().data.children.first().data
                tv_title.text = data.title

                Picasso.with(this@ThreadActivity)
                        .load(data.contentUrl)
                        // TODO: do image color blur on background
                        .transform(object: Transformation {
                            override fun key(): String {
                                return data.contentUrl
                            }

                            override fun transform(source: Bitmap): Bitmap {
                                val p = Palette.from(source).generate()
                                val defaultColor = resources.getColor(R.color.default_image_background)
                                thread_card.post {
                                    thread_card.setCardBackgroundColor(p.preferredColor)
                                }
                                return source
                            }

                        })
                        .into(img_post)
            }

        })
    }
}

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
