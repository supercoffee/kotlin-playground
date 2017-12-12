package com.bendaschel.kotlinplayground

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_thread.*

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
                        .into(img_post)
            }

        })
    }
}