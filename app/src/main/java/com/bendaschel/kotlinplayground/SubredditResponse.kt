package com.bendaschel.kotlinplayground

import com.google.gson.annotations.SerializedName

data class PostData (
        val id: String,
        val title: String,
        val thumbnail: String,
        @SerializedName("url") val contentUrl: String
)

data class Post (
        val kind: String,
        val data: PostData
)

data class ListingData (
        val after: String,
        val before: String,
        val children: List<Post>
)

data class ListingWrapper(
        val kind: String,
        val data: ListingData
)