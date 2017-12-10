package com.bendaschel.kotlinplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val redditApi by lazy {
        Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi::class.java)
    }

    val adapter by lazy {
        PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_main.adapter = adapter
        recycler_main.layoutManager = LinearLayoutManager(this)

        fetchData()
    }

    private fun fetchData() {
        redditApi.subreddit("aww")
                .enqueue(object: Callback<SubredditResponse> {
                    override fun onResponse(call: Call<SubredditResponse>?, response: Response<SubredditResponse>?) {
                        adapter.update(response?.body()?.data?.children!!)
                    }

                    override fun onFailure(call: Call<SubredditResponse>?, t: Throwable?) {
                        Log.e("MainActivity", "Fetching subreddits failed", t)
                    }

                })
    }

}
