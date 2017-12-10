package com.bendaschel.kotlinplayground

data class PostData (
        val id: String,
        val title: String,
        val thumbnail: String
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

data class SubredditResponse(
        val kind: String,
        val data: ListingData
)