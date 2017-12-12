package com.bendaschel.kotlinplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gildor.coroutines.retrofit.awaitResponse

class MainActivity : AppCompatActivity() {

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
        async(UI) {
            val response = getSubreddit("aww")
            adapter.update(response?.data?.children!!)
        }
    }

    private suspend fun getSubreddit(name: String): ListingWrapper? =
        redditApi.subreddit(name).awaitResponse().body()

}
