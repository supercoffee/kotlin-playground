package com.bendaschel.kotlinplayground

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PostAdapter: RecyclerView.Adapter<PostVH>() {

    private val posts = mutableListOf<Post>()

    fun update(posts: List<Post>) {
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        return PostVH(view)
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}

class PostVH (val view: View): RecyclerView.ViewHolder(view) {

    val imageView: ImageView = view.findViewById(R.id.post_image)
    val titleView: TextView = view.findViewById(R.id.post_title)

    fun bind(post: Post) {
        titleView.text = post.data.title
        Picasso.with(imageView.context).load(post.data.thumbnail).into(imageView)
    }

}
