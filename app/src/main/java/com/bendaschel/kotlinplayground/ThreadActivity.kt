package com.bendaschel.kotlinplayground

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.graphics.Palette
import android.support.v7.widget.CardView
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.activity_thread.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThreadActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        val id = intent.getStringExtra("threadId")

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
                        .transform(BackgroundColorTransformation(thread_card))
                        .into(img_post)
            }

        })
    }
}

class BackgroundColorTransformation(val card: CardView): Transformation {
    override fun key(): String {
        return toString()
    }

    override fun transform(source: Bitmap): Bitmap {
        val p = Palette.from(source).generate()
        val defaultColor = card.context.resources.getColor(R.color.default_image_background)
        card.post {
            card.setCardBackgroundColor(p.preferredColor)
        }
        return source
    }
}
